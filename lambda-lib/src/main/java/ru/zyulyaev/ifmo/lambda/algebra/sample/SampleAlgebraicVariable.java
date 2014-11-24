package ru.zyulyaev.ifmo.lambda.algebra.sample;

import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicExpressionVisitor;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicVariable;

/**
 * @author zyulyaev
 * @date 24.11.14 16:29
 */
public class SampleAlgebraicVariable implements AlgebraicVariable<SampleAlgebraicVariable, SampleAlgebraicFunction, SampleAlgebraicExpression>, SampleAlgebraicExpression {
    private final String name;

    public SampleAlgebraicVariable(String name) {
        this.name = name;
    }

    @Override
    public SampleAlgebraicExpression substitute(SampleAlgebraicVariable variable, SampleAlgebraicExpression expression) {
        return equals(variable) ? expression : this;
    }

    @Override
    public <T> T accept(AlgebraicExpressionVisitor<T, SampleAlgebraicVariable, SampleAlgebraicFunction, SampleAlgebraicExpression> visitor) {
        return visitor.visit(this);
    }

    @Override
    public SampleAlgebraicVariable asVariable() {
        return this;
    }

    @Override
    public SampleAlgebraicFunction asFunction() {
        throw new UnsupportedOperationException("Variable is not a function");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleAlgebraicVariable that = (SampleAlgebraicVariable) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
