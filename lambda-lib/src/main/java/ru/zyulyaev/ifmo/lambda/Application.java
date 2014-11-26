package ru.zyulyaev.ifmo.lambda;

/**
 * Created by nikita on 20.11.14.
 */
public class Application implements Expression {
    private final Expression left;
    private final Expression right;
    private int hash;

    public Application(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "(" + left + " " + right + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || hashCode() != o.hashCode()) return false;

        Application that = (Application) o;
        return left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = 31 * left.hashCode() + right.hashCode();
        }
        return hash;
    }
}
