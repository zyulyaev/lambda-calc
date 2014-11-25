package ru.zyulyaev.ifmo.lambda;

import java.util.Set;

/**
 * Created by nikita on 20.11.14.
 */
public interface Expression {
    /**
     * @return set of variables, which have free occurrences in this expression
     */
    Set<Variable> getFreeVariables();

    /**
     * @return set of all variables occurring in this expression
     */
    Set<Variable> getVariables();

    /**
     * Substitutes every free occurrence of given variable with given expression
     * @param variable variable to substitute
     * @param expression expression to substitute variable with
     * @return expression with given variable substituted with given expression
     * @throws FreshnessConditionException if substitution is impossible, i.e. some free variable in given expression becomes bound after substitution
     */
    Expression substitute(Variable variable, Expression expression) throws FreshnessConditionException;

    <T> T accept(ExpressionVisitor<T> visitor);

    /**
     * @return String representation of this expression with explicit parenthesis
     */
    String toString();
}
