package ru.zyulyaev.ifmo.lambda.algebra;

import java.util.List;

/**
 * @author zyulyaev
 * @date 24.11.14 16:05
 */
public interface AlgebraicFunction<V extends AlgebraicVariable<V, F, E>, F extends AlgebraicFunction<V, F, E>, E extends AlgebraicExpression<V, F, E>> extends AlgebraicExpression<V, F, E> {
    boolean isEqualId(F function);

    List<E> getArguments();

    @Override
    default boolean isVariable() {
        return false;
    }

    @Override
    default boolean isFunction() {
        return true;
    }
}
