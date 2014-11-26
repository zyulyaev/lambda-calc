package ru.zyulyaev.ifmo.lambda.analyzer;

import ru.zyulyaev.ifmo.lambda.*;

/**
 * @author zyulyaev
 * @date 26.11.14 19:16
 */
public class ExpressionSubstitutor {
    private final FreeVariablesFinder finder;

    public ExpressionSubstitutor(FreeVariablesFinder finder) {
        this.finder = finder;
    }

    /**
     * Substitutes every free occurrence of given variable with given expression
     * @param original original expression
     * @param variable variable to substitute
     * @param substitution expression to substitute variable with
     * @return expression with given variable substituted with given expression
     * @throws FreshnessConditionException if substitution is impossible, i.e. some free variable in given expression becomes bound after substitution
     */
    public Expression substitute(Expression original, Variable variable, Expression substitution) throws FreshnessConditionException {
        if (original.accept(new FreshnessChecker(variable, substitution))) {
            throw new FreshnessConditionException();
        }
        return original.accept(new SubstitutorVisitor(variable, substitution));
    }

    private class SubstitutorVisitor implements ExpressionVisitor<Expression> {
        private final Variable variable;
        private final Expression substitution;

        private SubstitutorVisitor(Variable variable, Expression substitution) {
            this.variable = variable;
            this.substitution = substitution;
        }

        @Override
        public Expression visit(Abstraction abstraction) {
            if (!finder.findFreeVariables(abstraction).contains(variable)) {
                return abstraction;
            }
            return new Abstraction(abstraction.getVariable(), abstraction.getExpression().accept(this));
        }

        @Override
        public Expression visit(Application application) {
            if (!finder.findFreeVariables(application).contains(variable)) {
                return application;
            }
            return new Application(application.getLeft().accept(this), application.getRight().accept(this));
        }

        @Override
        public Expression visit(Variable variable) {
            return variable.equals(this.variable) ? substitution : variable;
        }
    }

    private class FreshnessChecker implements ExpressionVisitor<Boolean> {
        private final Variable variable;
        private final Expression substitution;

        private FreshnessChecker(Variable variable, Expression substitution) {
            this.variable = variable;
            this.substitution = substitution;
        }

        @Override
        public Boolean visit(Abstraction abstraction) {
            return finder.findFreeVariables(abstraction).contains(variable)
                    && (finder.findFreeVariables(substitution).contains(abstraction.getVariable())
                    || abstraction.getExpression().accept(this));
        }

        @Override
        public Boolean visit(Application application) {
            return finder.findFreeVariables(application).contains(variable)
                    && (application.getLeft().accept(this)
                    || application.getRight().accept(this));
        }

        @Override
        public Boolean visit(Variable variable) {
            return Boolean.FALSE;
        }
    }
}
