package ru.zyulyaev.ifmo.lambda.algebra;

/**
 * Created by nikita on 22.11.14.
 */
public class AlgebraicEquation {
    private final AlgebraicExpression left;
    private final AlgebraicExpression right;

    public AlgebraicEquation(AlgebraicExpression left, AlgebraicExpression right) {
        this.left = left;
        this.right = right;
    }

    public AlgebraicEquation swap() {
        return new AlgebraicEquation(right, left);
    }

    public AlgebraicExpression getLeft() {
        return left;
    }

    public AlgebraicExpression getRight() {
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
