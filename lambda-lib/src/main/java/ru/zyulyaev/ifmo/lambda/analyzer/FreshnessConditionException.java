package ru.zyulyaev.ifmo.lambda.analyzer;

import ru.zyulyaev.ifmo.lambda.Variable;

/**
 * Created by nikita on 20.11.14.
 */
public class FreshnessConditionException extends Exception {
    private final Variable variable;

    public FreshnessConditionException(Variable variable) {
        super("Freshness condition violated for " + variable);
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }
}
