package ru.zyulyaev.ifmo.lambda.analyzer;

import ru.zyulyaev.ifmo.lambda.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zyulyaev
 * @date 26.11.14 18:34
 */
public class FreeVariablesFinder extends CachedExpressionAnalyzer<Set<Variable>> {
    public FreeVariablesFinder(int maxCacheSize) {
        super(maxCacheSize);
    }

    public FreeVariablesFinder() {
    }

    public Set<Variable> findFreeVariables(Expression expression) {
        return getOrCompute(expression);
    }

    @Override
    protected Set<Variable> compute(Expression expression) {
        return Collections.unmodifiableSet(expression.accept(visitor));
    }

    private final ExpressionVisitor<Set<Variable>> visitor = new ExpressionVisitor<Set<Variable>>() {
        @Override
        public Set<Variable> visit(Abstraction abstraction) {
            Set<Variable> result = new HashSet<>(findFreeVariables(abstraction.getExpression()));
            result.remove(abstraction.getVariable());
            return result;
        }

        @Override
        public Set<Variable> visit(Application application) {
            Set<Variable> result = new HashSet<>(findFreeVariables(application.getLeft()));
            result.addAll(findFreeVariables(application.getRight()));
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
