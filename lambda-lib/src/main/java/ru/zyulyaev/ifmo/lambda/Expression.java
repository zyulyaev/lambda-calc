package ru.zyulyaev.ifmo.lambda;

/**
 * Created by nikita on 20.11.14.
 */
public interface Expression {
    <T> T accept(ExpressionVisitor<T> visitor);

    /**
     * @return String representation of this expression with explicit parenthesis
     */
    String toString();
}
