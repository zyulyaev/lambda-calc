package ru.zyulyaev.ifmo.lambda;

/**
 * Created by nikita on 20.11.14.
 */
public class Abstraction implements Expression {
    private final Variable variable;
    private final Expression expression;
    private int hash;

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
        if (o == null || getClass() != o.getClass() || hashCode() != o.hashCode()) return false;

        Abstraction that = (Abstraction) o;
        return variable.equals(that.variable) && expression.equals(that.expression);
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = 31 * variable.hashCode() + expression.hashCode();
        }
        return hash;
    }
}
