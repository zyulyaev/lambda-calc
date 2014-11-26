package ru.zyulyaev.ifmo.lambda.analyzer;

import ru.zyulyaev.ifmo.lambda.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Builds equivalent lambda expression with different variable names, so as no bound variable occurs free and no bound variable is bound twice
 */
public class ExpressionBeautifier extends CachedExpressionAnalyzer<Expression> {
    private final FreeVariablesFinder finder;

    public ExpressionBeautifier(FreeVariablesFinder finder, int maxCacheSize) {
        super(maxCacheSize);
        this.finder = finder;
    }

    public ExpressionBeautifier(FreeVariablesFinder finder) {
        this.finder = finder;
    }

    public Expression beautify(Expression expression) {
        return getOrCompute(expression);
    }

    @Override
    protected Expression compute(Expression expression) {
        Set<String> avoid = finder.findFreeVariables(expression).stream().map(Variable::getName).collect(Collectors.toSet());
        return expression.accept(new BeautifyingVisitor(new VariableNameSupplier(avoid)));
    }

    private static class BeautifyingVisitor implements ExpressionVisitor<Expression> {
        private final BeautifyingVisitor parent;
        private final Supplier<String> variableSupplier;
        private final Map<Variable, Variable> variables = new HashMap<>();

        private BeautifyingVisitor(BeautifyingVisitor parent, Variable key, Variable value) {
            this.parent = parent;
            this.variableSupplier = parent.variableSupplier;
            variables.put(key, value);
        }

        public BeautifyingVisitor(Supplier<String> variableSupplier) {
            this.parent = null;
            this.variableSupplier = variableSupplier;
        }

        @Override
        public Expression visit(Abstraction abstraction) {
            Variable variable = new Variable(variableSupplier.get());
            BeautifyingVisitor child = new BeautifyingVisitor(this, abstraction.getVariable(), variable);
            return new Abstraction(variable, abstraction.getExpression().accept(child));
        }

        @Override
        public Expression visit(Application application) {
            return new Application(application.getLeft().accept(this), application.getRight().accept(this));
        }

        private Variable resolveVariable(Variable variable) {
            if (variables.containsKey(variable)) return variables.get(variable);
            return parent != null ? parent.resolveVariable(variable) : variable;
        }

        @Override
        public Expression visit(Variable variable) {
            return variables.computeIfAbsent(variable, this::resolveVariable);
        }
    }
}
