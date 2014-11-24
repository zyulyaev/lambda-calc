package ru.zyulyaev.ifmo.lambda.algebra;

/**
 * Created by nikita on 22.11.14.
 */
public interface AlgebraicExpression<V extends AlgebraicVariable<V, F, E>, F extends AlgebraicFunction<V, F, E>, E extends AlgebraicExpression<V, F, E>> {
    E substitute(V variable, E expression);

    <T> T accept(AlgebraicExpressionVisitor<T, V, F, E> visitor);

    boolean isVariable();

    boolean isFunction();

    V asVariable();

    F asFunction();
}
