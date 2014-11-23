package ru.zyulyaev.ifmo.lambda;

import java.util.Collections;
import java.util.HashSet;
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
}
