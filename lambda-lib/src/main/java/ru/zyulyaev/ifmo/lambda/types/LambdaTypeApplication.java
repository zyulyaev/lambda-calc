package ru.zyulyaev.ifmo.lambda.types;

import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicExpressionVisitor;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zyulyaev
 * @date 24.11.14 16:48
 */
public class LambdaTypeApplication implements AlgebraicFunction<LambdaTypeVariable, LambdaTypeApplication, LambdaType>, LambdaType {
    private final List<LambdaType> arguments;

    public LambdaTypeApplication(LambdaType left, LambdaType right) {
        this.arguments = new ArrayList<>();
        arguments.add(left);
        arguments.add(right);
    }

    private LambdaTypeApplication(List<LambdaType> arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean isEqualId(LambdaTypeApplication function) {
        return true;
    }

    @Override
    public List<LambdaType> getArguments() {
        return arguments;
    }

    @Override
    public LambdaType substitute(LambdaTypeVariable variable, LambdaType expression) {
        return new LambdaTypeApplication(arguments.stream().map(arg -> arg.substitute(variable, expression)).collect(Collectors.toList()));
    }

    @Override
    public <T> T accept(AlgebraicExpressionVisitor<T, LambdaTypeVariable, LambdaTypeApplication, LambdaType> visitor) {
        return visitor.visit(this);
    }

    @Override
    public LambdaTypeVariable asVariable() {
        throw new UnsupportedOperationException("Application is not a variable");
    }

    @Override
    public LambdaTypeApplication asFunction() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LambdaTypeApplication that = (LambdaTypeApplication) o;
        return arguments.equals(that.arguments);
    }

    @Override
    public int hashCode() {
        return arguments.hashCode();
    }

    @Override
    public String toString() {
        return "(" + arguments.get(0) + ")->(" + arguments.get(1) + ")";
    }
}
