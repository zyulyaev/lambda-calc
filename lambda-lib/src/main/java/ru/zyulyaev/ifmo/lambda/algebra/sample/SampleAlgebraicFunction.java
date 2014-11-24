package ru.zyulyaev.ifmo.lambda.algebra.sample;

import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicExpressionVisitor;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicFunction;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zyulyaev
 * @date 24.11.14 16:29
 */
public class SampleAlgebraicFunction implements AlgebraicFunction<SampleAlgebraicVariable, SampleAlgebraicFunction, SampleAlgebraicExpression>, SampleAlgebraicExpression {
    private final String name;
    private final List<SampleAlgebraicExpression> arguments;

    public SampleAlgebraicFunction(String name, List<SampleAlgebraicExpression> arguments) {
        this.name = name;
        this.arguments = Collections.unmodifiableList(arguments);
    }

    @Override
    public boolean isEqualId(SampleAlgebraicFunction function) {
        return name.equals(function.name);
    }

    @Override
    public List<SampleAlgebraicExpression> getArguments() {
        return arguments;
    }

    @Override
    public SampleAlgebraicExpression substitute(SampleAlgebraicVariable variable, SampleAlgebraicExpression expression) {
        return new SampleAlgebraicFunction(name, arguments.stream().map(arg -> arg.substitute(variable, expression)).collect(Collectors.toList()));
    }

    @Override
    public <T> T accept(AlgebraicExpressionVisitor<T, SampleAlgebraicVariable, SampleAlgebraicFunction, SampleAlgebraicExpression> visitor) {
        return visitor.visit(this);
    }

    @Override
    public SampleAlgebraicVariable asVariable() {
        throw new UnsupportedOperationException("Function is not a variable");
    }

    @Override
    public SampleAlgebraicFunction asFunction() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleAlgebraicFunction that = (SampleAlgebraicFunction) o;
        return name.equals(that.name) && arguments.equals(that.arguments);
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + arguments.hashCode();
    }

    @Override
    public String toString() {
        return name + "(" + arguments.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
    }
}
