package ru.zyulyaev.ifmo.lambda;

import java.util.Set;

/**
 * Created by nikita on 20.11.14.
 */
public interface Expression {
    Set<String> getFreeVariables();

    Expression substitute(String variable, Expression expression) throws FreshnessConditionException;

    Expression normalize();

    Expression toSki();

    <T> T accept(ExpressionVisitor<T> visitor);
}
