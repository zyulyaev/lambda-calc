package ru.zyulyaev.ifmo.lambda.algebra;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nikita on 22.11.14.
 */
public class AlgebraicSystem<V extends AlgebraicVariable<V, F, E>, F extends AlgebraicFunction<V, F, E>, E extends AlgebraicExpression<V, F, E>> {
    private final List<AlgebraicEquation<V, F, E>> equations;

    public AlgebraicSystem(List<AlgebraicEquation<V, F, E>> equations) {
        this.equations = new ArrayList<>(equations);
    }

    public AlgebraicSystem trySolve() {
        AlgebraicSystem<V, F, E> solution = new AlgebraicSystem<>(equations);
        //noinspection StatementWithEmptyBody
        while (!solution.isSolved() && !solution.isInconsistent()) {
            solution.tryRuleA();
            solution.tryRuleB();
            solution.tryRuleC();
            solution.tryRuleD();
            solution.tryRuleE();
        }
        return solution;
    }

    public List<AlgebraicEquation<V, F, E>> getEquations() {
        return Collections.unmodifiableList(equations);
    }

    public boolean isSolved() {
        Set<AlgebraicVariable<V, F, E>> variables = new HashSet<>();
        for (AlgebraicEquation<V, F, E> eq : equations) {
            if (!eq.getLeft().isVariable() || !variables.add(eq.getLeft().asVariable())) {
                return false;
            }
        }
        for (AlgebraicEquation<V, F, E> eq : equations) {
            if (eq.getRight().accept(new AlgebraicExpressionVisitor<Boolean, V, F, E>() {
                @Override
                public Boolean visit(V variable) {
                    return variables.contains(variable);
                }

                @Override
                public Boolean visit(F function) {
                    return function.getArguments().stream().anyMatch(e -> e.accept(this));
                }
            })) {
                return false;
            }
        }
        return true;
    }

    public boolean isInconsistent() {
        for (AlgebraicEquation<V, F, E> eq : equations) {
            if (eq.getLeft().isFunction() && eq.getRight().isFunction()) {
                F left = eq.getLeft().asFunction();
                F right = eq.getRight().asFunction();
                if (!left.isEqualId(right)) {
                    return true;
                }
            }
            if (eq.getLeft().isVariable() && eq.getRight().isFunction()) {
                V left = eq.getLeft().asVariable();
                F right = eq.getRight().asFunction();
                if (right.accept(new AlgebraicExpressionVisitor<Boolean, V, F, E>() {
                    @Override
                    public Boolean visit(V variable) {
                        return variable.equals(left);
                    }

                    @Override
                    public Boolean visit(F function) {
                        return function.getArguments().stream().anyMatch(x -> x.accept(this));
                    }
                })) return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlgebraicSystem that = (AlgebraicSystem) o;
        return equations.equals(that.equations);
    }

    @Override
    public int hashCode() {
        return equations.hashCode();
    }

    @Override
    public String toString() {
        return equations.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    private boolean tryRuleA() {
        boolean result = false;
        for (int i = 0, len = equations.size(); i < len; ++i) {
            AlgebraicEquation<V, F, E> a = equations.get(i);
            if (a.getLeft().isFunction() || a.getRight().isVariable()) continue;
            for (ListIterator<AlgebraicEquation<V, F, E>> j = equations.listIterator(); j.hasNext(); ) {
                AlgebraicEquation<V, F, E> b = j.next();
                if (a == b || !a.getLeft().equals(b.getLeft())) continue;
                j.remove();
                j.add(new AlgebraicEquation<>(a.getRight(), b.getRight()));
                result = true;
            }
        }
        return result;
    }

    private boolean tryRuleB() {
        boolean result = false;
        for (ListIterator<AlgebraicEquation<V, F, E>> i = equations.listIterator(); i.hasNext(); ) {
            AlgebraicEquation<V, F, E> eq = i.next();
            if (eq.getLeft().isFunction() && eq.getRight().isVariable()) {
                i.remove();
                i.add(eq.swap());
                result = true;
            }
        }
        return result;
    }

    private boolean tryRuleC() {
        boolean result = false;
        for (ListIterator<AlgebraicEquation<V, F, E>> i = equations.listIterator(); i.hasNext(); ) {
            AlgebraicEquation<V, F, E> eq = i.next();
            if (eq.getLeft().isFunction() && eq.getRight().isFunction()) {
                F left = eq.getLeft().asFunction();
                F right = eq.getRight().asFunction();
                if (left.isEqualId(right) && left.getArguments().size() == right.getArguments().size()) {
                    i.remove();
                    for (int j = 0, len = left.getArguments().size(); j < len; ++j) {
                        i.add(new AlgebraicEquation<>(left.getArguments().get(j), right.getArguments().get(j)));
                    }
                    result = true;
                }
            }
        }
        return result;
    }

    private boolean tryRuleD() {
        boolean result = false;
        for (int i = 0, len = equations.size(); i < len; ++i) {
            AlgebraicEquation<V, F, E> a = equations.get(i);
            if (a.getLeft().isFunction()) continue;
            V var = a.getLeft().asVariable();
            for (ListIterator<AlgebraicEquation<V, F, E>> j = equations.listIterator(); j.hasNext(); ) {
                AlgebraicEquation<V, F, E> b = j.next();
                if (a == b) continue;
                AlgebraicEquation<V, F, E> newEquation = new AlgebraicEquation<>(
                        b.getLeft().substitute(var, a.getRight()),
                        b.getRight().substitute(var, a.getRight())
                );
                if (!b.equals(newEquation)) {
                    j.remove();
                    j.add(newEquation);
                    result = true;
                }
            }
        }
        return result;
    }

    private boolean tryRuleE() {
        boolean result = false;
        for (ListIterator<AlgebraicEquation<V, F, E>> i = equations.listIterator(); i.hasNext(); ) {
            AlgebraicEquation<V, F, E> eq = i.next();
            if (eq.getLeft().equals(eq.getRight())) {
                i.remove();
                result = true;
            }
        }
        return result;
    }
}
