package ru.zyulyaev.ifmo.lambda.algebra;

/**
 * Created by nikita on 22.11.14.
 */
public interface AlgebraicExpression {
    AlgebraicExpression substitute(String variable, AlgebraicExpression expression);

    <T> T accept(AlgebraicExpressionVisitor<T> visitor);

    boolean isVariable();

    boolean isFunction();
}
