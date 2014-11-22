package ru.zyulyaev.ifmo.lambda.algebra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nikita on 22.11.14.
 */
public class AlgebraicFunction implements AlgebraicExpression {
    private final String name;
    private final List<AlgebraicExpression> arguments;

    public AlgebraicFunction(String name, List<AlgebraicExpression> arguments) {
        this.name = name;
        this.arguments = Collections.unmodifiableList(new ArrayList<>(arguments));
    }

    public String getName() {
        return name;
    }

    public List<AlgebraicExpression> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return name + "(" + arguments.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
    }

    @Override
    public AlgebraicExpression substitute(String variable, AlgebraicExpression expression) {
        return new AlgebraicFunction(name, arguments.stream().map(x -> x.substitute(variable, expression)).collect(Collectors.toList()));
    }

    @Override
    public <T> T accept(AlgebraicExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    @Override
    public boolean isFunction() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlgebraicFunction that = (AlgebraicFunction) o;
        return arguments.equals(that.arguments) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + arguments.hashCode();
    }
}
