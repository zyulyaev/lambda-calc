package ru.zyulyaev.ifmo.lambda;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by nikita on 20.11.14.
 */
public class Application implements Expression {
    private final Expression left;
    private final Expression right;
    private Set<String> freeVariables;

    public Application(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public Set<String> getFreeVariables() {
        if (freeVariables == null) {
            Set<String> vars = new HashSet<>(left.getFreeVariables());
            vars.addAll(right.getFreeVariables());
            this.freeVariables = Collections.unmodifiableSet(vars);
        }
        return freeVariables;
    }

    @Override
    public Expression substitute(String variable, Expression expression) throws FreshnessConditionException {
        if (!this.getFreeVariables().contains(variable)) {
            return this;
        }
        return new Application(left.substitute(variable, expression), right.substitute(variable, expression));
    }

    @Override
    public Expression normalize() {
        final Expression leftNormalized = left.normalize();
        final Expression rightNormalized = right.normalize();
        return leftNormalized.accept(new NormalizeVisitor(rightNormalized))
                .orElseGet(() -> new Application(leftNormalized, rightNormalized));
    }

    @Override
    public Expression toSki() {
        return new Application(left.toSki(), right.toSki());
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "(" + left + " " + right + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;
        return left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return 31 * left.hashCode() + right.hashCode();
    }

    private static class NormalizeVisitor implements ExpressionVisitor<Optional<Expression>> {
        private final Expression rightNormalized;

        public NormalizeVisitor(Expression rightNormalized) {
            this.rightNormalized = rightNormalized;
        }

        @Override
        public Optional<Expression> visit(Abstraction abstraction) {
            try {
                return Optional.of(abstraction.getExpression().substitute(abstraction.getVariable(), rightNormalized));
            } catch (FreshnessConditionException e) {
                return Optional.empty();
            }
        }

        @Override
        public Optional<Expression> visit(Application application) {
            return Optional.empty();
        }

        @Override
        public Optional<Expression> visit(Variable variable) {
            return Optional.empty();
        }
    }
}
