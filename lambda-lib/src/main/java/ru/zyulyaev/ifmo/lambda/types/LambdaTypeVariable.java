package ru.zyulyaev.ifmo.lambda.types;

import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicExpressionVisitor;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicVariable;

/**
 * @author zyulyaev
 * @date 24.11.14 16:47
 */
public class LambdaTypeVariable implements AlgebraicVariable<LambdaTypeVariable, LambdaTypeApplication, LambdaType>, LambdaType {
    private final String name;

    public LambdaTypeVariable(String name) {
        this.name = name;
    }

    @Override
    public LambdaType substitute(LambdaTypeVariable variable, LambdaType expression) {
        return equals(variable) ? expression : this;
    }

    @Override
    public <T> T accept(AlgebraicExpressionVisitor<T, LambdaTypeVariable, LambdaTypeApplication, LambdaType> visitor) {
        return visitor.visit(this);
    }

    @Override
    public LambdaTypeVariable asVariable() {
        return this;
    }

    @Override
    public LambdaTypeApplication asFunction() {
        throw new UnsupportedOperationException("Variable is not a function");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LambdaTypeVariable that = (LambdaTypeVariable) o;
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
