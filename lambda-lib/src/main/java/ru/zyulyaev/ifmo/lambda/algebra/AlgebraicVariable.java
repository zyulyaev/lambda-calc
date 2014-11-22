package ru.zyulyaev.ifmo.lambda.algebra;

/**
 * Created by nikita on 22.11.14.
 */
public class AlgebraicVariable implements AlgebraicExpression {
    private final String name;

    public AlgebraicVariable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public AlgebraicExpression substitute(String variable, AlgebraicExpression expression) {
        return name.equals(variable) ? expression : this;
    }

    @Override
    public <T> T accept(AlgebraicExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    @Override
    public boolean isFunction() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlgebraicVariable that = (AlgebraicVariable) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
