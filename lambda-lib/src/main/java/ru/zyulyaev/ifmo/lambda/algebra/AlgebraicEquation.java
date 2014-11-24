package ru.zyulyaev.ifmo.lambda.algebra;

/**
 * Created by nikita on 22.11.14.
 */
public class AlgebraicEquation<V extends AlgebraicVariable<V, F, E>, F extends AlgebraicFunction<V, F, E>, E extends AlgebraicExpression<V, F, E>> {
    private final E left;
    private final E right;

    public AlgebraicEquation(E left, E right) {
        this.left = left;
        this.right = right;
    }

    public AlgebraicEquation<V, F, E> swap() {
        return new AlgebraicEquation<>(right, left);
    }

    public E getLeft() {
        return left;
    }

    public E getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlgebraicEquation that = (AlgebraicEquation) o;
        return left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return 31 * left.hashCode() + right.hashCode();
    }

    @Override
    public String toString() {
        return left + "=" + right;
    }
}
