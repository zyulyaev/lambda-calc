package ru.zyulyaev.ifmo.lambda;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nikita on 20.11.14.
 */
public class Abstraction implements Expression {
    private final Variable variable;
    private final Expression expression;
    private Set<Variable> freeVariables;
    private Set<Variable> variables;

    public Abstraction(Variable variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public Variable getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Set<Variable> getFreeVariables() {
        if (freeVariables == null) {
            Set<Variable> vars = new HashSet<>(expression.getFreeVariables());
            vars.remove(variable);
            this.freeVariables = Collections.unmodifiableSet(vars);
        }
        return freeVariables;
    }

    @Override
    public Set<Variable> getVariables() {
        if (variables == null) {
            Set<Variable> vars = new HashSet<>(expression.getVariables());
            vars.add(variable);
            this.variables = vars;
        }
        return variables;
    }

    @Override
    public Expression substitute(Variable variable, Expression expression) throws FreshnessConditionException {
        if (!this.getFreeVariables().contains(variable)) {
            return this;
        }
        if (expression.getFreeVariables().contains(this.variable)) {
            throw new FreshnessConditionException();
        }
        return new Abstraction(this.variable, this.expression.substitute(variable, expression));
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "(\\" + variable + "." + expression + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Abstraction that = (Abstraction) o;
        return variable.equals(that.variable) && expression.equals(that.expression);
    }

    @Override
    public int hashCode() {
        return 31 * variable.hashCode() + expression.hashCode();
    }
}
