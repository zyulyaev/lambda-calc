package ru.zyulyaev.ifmo.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Builds equivalent lambda expression with different variable names, so as no bound variable occurs free and no bound variable is bound twice
 */
public class ExpressionBeautifier implements ExpressionVisitor<Expression> {
    private final ExpressionBeautifier parent;
    private final Supplier<String> variableSupplier;
    private final Map<String, Variable> variables = new HashMap<>();

    private ExpressionBeautifier(ExpressionBeautifier parent, String key, Variable value) {
        this.parent = parent;
        this.variableSupplier = parent.variableSupplier;
        variables.put(key, value);
    }

    public ExpressionBeautifier() {
        this.parent = null;
        this.variableSupplier = new VariableNameSupplier();
    }

    @Override
    public Expression visit(Abstraction abstraction) {
        Variable variable = new Variable(variableSupplier.get());
        ExpressionBeautifier child = new ExpressionBeautifier(this, abstraction.getVariable(), variable);
        return new Abstraction(variable.getName(), abstraction.getExpression().accept(child));
    }

    @Override
    public Expression visit(Application application) {
        return new Application(application.getLeft().accept(this), application.getRight().accept(this));
    }

    private Variable resolveVariable(String name) {
        if (variables.containsKey(name)) return variables.get(name);
        if (parent != null) return parent.resolveVariable(name);
        return variables.computeIfAbsent(name, n -> new Variable(variableSupplier.get()));
    }

    @Override
    public Expression visit(Variable variable) {
        return variables.computeIfAbsent(variable.getName(), this::resolveVariable);
    }
}
