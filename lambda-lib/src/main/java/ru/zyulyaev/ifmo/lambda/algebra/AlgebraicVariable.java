package ru.zyulyaev.ifmo.lambda.algebra;

/**
 * @author zyulyaev
 * @date 24.11.14 16:02
 */
public interface AlgebraicVariable<V extends AlgebraicVariable<V, F, E>, F extends AlgebraicFunction<V, F, E>, E extends AlgebraicExpression<V, F, E>> extends AlgebraicExpression<V, F, E> {
    @Override
    default boolean isVariable() {
        return true;
    }

    @Override
    default boolean isFunction() {
        return false;
    }
}
