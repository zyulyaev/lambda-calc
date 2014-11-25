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
    private Set<Variable> freeVariables;
    private Set<Variable> variables;

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
    public Set<Variable> getFreeVariables() {
        if (freeVariables == null) {
            Set<Variable> vars = new HashSet<>(left.getFreeVariables());
            vars.addAll(right.getFreeVariables());
            this.freeVariables = Collections.unmodifiableSet(vars);
        }
        return freeVariables;
    }

    @Override
    public Set<Variable> getVariables() {
        if (variables == null) {
            Set<Variable> vars = new HashSet<>(left.getVariables());
            vars.addAll(right.getVariables());
            this.variables = vars;
        }
        return variables;
    }

    @Override
    public Expression substitute(Variable variable, Expression expression) throws FreshnessConditionException {
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
