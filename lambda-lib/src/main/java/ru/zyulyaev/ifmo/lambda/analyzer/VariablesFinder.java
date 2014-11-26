package ru.zyulyaev.ifmo.lambda.analyzer;

import ru.zyulyaev.ifmo.lambda.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zyulyaev
 * @date 26.11.14 18:30
 */
public class VariablesFinder extends CachedExpressionAnalyzer<Set<Variable>> {
    public VariablesFinder(int maxCacheSize) {
        super(maxCacheSize);
    }

    public VariablesFinder() {
    }

    public Set<Variable> findVariables(Expression expression) {
        return getOrCompute(expression);
    }

    @Override
    protected Set<Variable> compute(Expression expression) {
        return Collections.unmodifiableSet(expression.accept(visitor));
    }

    private final ExpressionVisitor<Set<Variable>> visitor = new ExpressionVisitor<Set<Variable>>() {
        @Override
        public Set<Variable> visit(Abstraction abstraction) {
            Set<Variable> result = new HashSet<>(findVariables(abstraction.getExpression()));
            result.add(abstraction.getVariable());
            return result;
        }

        @Override
        public Set<Variable> visit(Application application) {
            Set<Variable> result = new HashSet<>(findVariables(application.getLeft()));
            result.addAll(findVariables(application.getRight()));
            return result;
        }

        @Override
        public Set<Variable> visit(Variable variable) {
            Set<Variable> result = new HashSet<>();
            result.add(variable);
            return result;
        }
    };
}
