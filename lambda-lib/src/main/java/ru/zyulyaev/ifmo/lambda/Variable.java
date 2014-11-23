package ru.zyulyaev.ifmo.lambda;

import java.util.Collections;
import java.util.Set;

/**
 * Created by nikita on 20.11.14.
 */
public class Variable implements Expression {
    private final String name;
    private final Set<String> freeVariables;

    public Variable(String name) {
        this.name = name;
        this.freeVariables = Collections.singleton(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public Set<String> getFreeVariables() {
        return freeVariables;
    }

    @Override
    public Expression substitute(String variable, Expression expression) throws FreshnessConditionException {
        return name.equals(variable) ? expression : this;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;
        return name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
