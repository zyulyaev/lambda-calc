package ru.zyulyaev.ifmo.lambda.algebra;

/**
 * Created by nikita on 22.11.14.
 */
public interface AlgebraicExpressionVisitor<T> {
    T visit(AlgebraicVariable variable);

    T visit(AlgebraicFunction function);
}
