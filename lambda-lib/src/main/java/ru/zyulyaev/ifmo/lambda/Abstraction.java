package ru.zyulyaev.ifmo.lambda;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nikita on 20.11.14.
 */
public class Abstraction implements Expression {
    private final String variable;
    private final Expression expression;
    private Set<String> freeVariables;
    private final ExpressionVisitor<Expression> skiVisitor = new ExpressionVisitor<Expression>() {
        @Override
        public Expression visit(Abstraction abstraction) {
            return new Abstraction(variable, abstraction.toSki()).toSki();
        }

        @Override
        public Expression visit(Application application) {
            return new Application(
                    new Application(
                            new Variable("S"),
                            new Abstraction(variable, application.getLeft()).toSki()
                    ),
                    new Abstraction(variable, application.getRight()).toSki()
            );
        }

        @Override
        public Expression visit(Variable variable) {
            return new Variable("I");
        }
    };

    public Abstraction(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public String getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Set<String> getFreeVariables() {
        if (freeVariables == null) {
            Set<String> vars = new HashSet<>(expression.getFreeVariables());
            vars.remove(variable);
            this.freeVariables = Collections.unmodifiableSet(vars);
        }
        return freeVariables;
    }

    @Override
    public Expression substitute(String variable, Expression expression) throws FreshnessConditionException {
        if (!this.getFreeVariables().contains(variable)) {
            return this;
        }
        if (expression.getFreeVariables().contains(this.variable)) {
            throw new FreshnessConditionException();
        }
        return new Abstraction(this.variable, this.expression.substitute(variable, expression));
    }

    @Override
    public Expression normalize() {
        Expression normalized = expression.normalize();
        return normalized == expression ? this : new Abstraction(variable, normalized);
    }

    @Override
    public Expression toSki() {
        if (expression.getFreeVariables().contains(variable)) {
            return expression.accept(skiVisitor);
        } else {
            return new Application(new Variable("K"), expression.toSki());
        }
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
        return expression.equals(that.expression) && variable.equals(that.variable);
    }

    @Override
    public int hashCode() {
        return 31 * variable.hashCode() + expression.hashCode();
    }
}
