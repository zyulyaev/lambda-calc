package ru.zyulyaev.ifmo.lambda;

/**
 * Created by nikita on 20.11.14.
 */
public interface ExpressionVisitor<T> {
    T visit(Abstraction abstraction);

    T visit(Application application);

    T visit(Variable variable);
}
