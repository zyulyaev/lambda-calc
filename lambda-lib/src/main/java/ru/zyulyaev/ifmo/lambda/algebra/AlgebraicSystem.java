package ru.zyulyaev.ifmo.lambda.algebra;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nikita on 22.11.14.
 */
public class AlgebraicSystem {
    private final List<AlgebraicEquation> equations;

    public AlgebraicSystem(List<AlgebraicEquation> equations) {
        this.equations = new ArrayList<>(equations);
    }

    public AlgebraicSystem trySolve() {
        AlgebraicSystem solution = new AlgebraicSystem(equations);
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

    public List<AlgebraicEquation> getEquations() {
        return Collections.unmodifiableList(equations);
    }

    public boolean isSolved() {
        Set<AlgebraicVariable> variables = new HashSet<>();
        for (AlgebraicEquation eq : equations) {
            if (!eq.getLeft().isVariable() || !variables.add((AlgebraicVariable) eq.getLeft())) {
                return false;
            }
        }
        for (AlgebraicEquation eq : equations) {
            if (eq.getRight().accept(new AlgebraicExpressionVisitor<Boolean>() {
                @Override
                public Boolean visit(AlgebraicVariable variable) {
                    return variables.contains(variable);
                }

                @Override
                public Boolean visit(AlgebraicFunction function) {
                    return function.getArguments().stream().anyMatch(e -> e.accept(this));
                }
            })) {
                return false;
            }
        }
        return true;
    }

    public boolean isInconsistent() {
        for (AlgebraicEquation eq : equations) {
            if (eq.getLeft().isFunction() && eq.getRight().isFunction()) {
                AlgebraicFunction left = (AlgebraicFunction) eq.getLeft();
                AlgebraicFunction right = (AlgebraicFunction) eq.getRight();
                if (!left.getName().equals(right.getName())) {
                    return true;
                }
            }
            if (eq.getLeft().isVariable() && eq.getRight().isFunction()) {
                AlgebraicVariable left = (AlgebraicVariable) eq.getLeft();
                AlgebraicFunction right = (AlgebraicFunction) eq.getRight();
                if (right.accept(new AlgebraicExpressionVisitor<Boolean>() {
                    @Override
                    public Boolean visit(AlgebraicVariable variable) {
                        return variable.equals(left);
                    }

                    @Override
                    public Boolean visit(AlgebraicFunction function) {
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
            AlgebraicEquation a = equations.get(i);
            if (a.getLeft().isFunction() || a.getRight().isVariable()) continue;
            for (ListIterator<AlgebraicEquation> j = equations.listIterator(); j.hasNext(); ) {
                AlgebraicEquation b = j.next();
                if (a == b || !a.getLeft().equals(b.getLeft())) continue;
                j.remove();
                j.add(new AlgebraicEquation(a.getRight(), b.getRight()));
                result = true;
            }
        }
        return result;
    }

    private boolean tryRuleB() {
        boolean result = false;
        for (ListIterator<AlgebraicEquation> i = equations.listIterator(); i.hasNext(); ) {
            AlgebraicEquation eq = i.next();
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
        for (ListIterator<AlgebraicEquation> i = equations.listIterator(); i.hasNext(); ) {
            AlgebraicEquation eq = i.next();
            if (eq.getLeft().isFunction() && eq.getRight().isFunction()) {
                AlgebraicFunction left = (AlgebraicFunction) eq.getLeft();
                AlgebraicFunction right = (AlgebraicFunction) eq.getRight();
                if (left.getName().equals(right.getName()) && left.getArguments().size() == right.getArguments().size()) {
                    i.remove();
                    for (int j = 0, len = left.getArguments().size(); j < len; ++j) {
                        i.add(new AlgebraicEquation(left.getArguments().get(j), right.getArguments().get(j)));
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
            AlgebraicEquation a = equations.get(i);
            if (a.getLeft().isFunction()) continue;
            String var = ((AlgebraicVariable) a.getLeft()).getName();
            for (ListIterator<AlgebraicEquation> j = equations.listIterator(); j.hasNext(); ) {
                AlgebraicEquation b = j.next();
                if (a == b) continue;
                AlgebraicEquation newEquation = new AlgebraicEquation(
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
        for (ListIterator<AlgebraicEquation> i = equations.listIterator(); i.hasNext(); ) {
            AlgebraicEquation eq = i.next();
            if (eq.getLeft().equals(eq.getRight())) {
                i.remove();
                result = true;
            }
        }
        return result;
    }
}
