package ru.zyulyaev.ifmo.lambda;

/**
 * Created by nikita on 24.11.14.
 */
public enum StandardExpressionVisitor implements ExpressionVisitor<Expression> {
    /**
     * Builds equivalent lambda expression made of S, K, I combinators without abstractions
     */
    SKI_CONVERTER {
        @Override
        public Expression visit(Abstraction abstraction) {
            Variable variable = abstraction.getVariable();
            Expression expression = abstraction.getExpression();
            if (expression.getFreeVariables().contains(variable)) {
                return expression.accept(new AbstractionSkiVisitor(variable));
            } else {
                return new Application(new Variable("K"), expression.accept(SKI_CONVERTER));
            }
        }

        @Override
        public Expression visit(Application application) {
            return new Application(application.getLeft().accept(SKI_CONVERTER), application.getRight().accept(SKI_CONVERTER));
        }

        @Override
        public Expression visit(Variable variable) {
            return variable;
        }

        class AbstractionSkiVisitor implements ExpressionVisitor<Expression> {
            private final Variable variable;

            private AbstractionSkiVisitor(Variable variable) {
                this.variable = variable;
            }

            @Override
            public Expression visit(Abstraction abstraction) {
                return new Abstraction(variable, abstraction.accept(SKI_CONVERTER)).accept(SKI_CONVERTER);
            }

            @Override
            public Expression visit(Application application) {
                return new Application(
                        new Application(
                                new Variable("S"),
                                new Abstraction(variable, application.getLeft()).accept(SKI_CONVERTER)
                        ),
                        new Abstraction(variable, application.getRight()).accept(SKI_CONVERTER)
                );
            }

            @Override
            public Expression visit(Variable variable) {
                return new Variable("I");
            }
        }
    },
    /**
     * Build equivalent lambda expression with all possible beta-reductions done
     */
    NORMALIZER {
        @Override
        public Expression visit(Abstraction abstraction) {
            Variable variable = abstraction.getVariable();
            Expression expression = abstraction.getExpression();

            Expression normalized = expression.accept(NORMALIZER);
            return normalized == expression ? abstraction : new Abstraction(variable, normalized);
        }

        @Override
        public Expression visit(Application application) {
            Expression redex = application.getLeft().accept(new ApplicationNormalizeVisitor(application.getRight()));
            if (redex == null) {
                Expression left = application.getLeft().accept(NORMALIZER);
                Expression right = application.getRight();
                if (left == application.getLeft()) {
                    right = right.accept(NORMALIZER);
                }
                if (left == application.getLeft() && right == application.getRight()) {
                    return application;
                }
                return new Application(left, right).accept(NORMALIZER);
            } else {
                return redex.accept(NORMALIZER);
            }
        }

        @Override
        public Expression visit(Variable variable) {
            return variable;
        }

        class ApplicationNormalizeVisitor implements ExpressionVisitor<Expression> {
            private final Expression rightExpression;

            ApplicationNormalizeVisitor(Expression rightExpression) {
                this.rightExpression = rightExpression;
            }

            @Override
            public Expression visit(Abstraction abstraction) {
                try {
                    return abstraction.getExpression().substitute(abstraction.getVariable(), rightExpression);
                } catch (FreshnessConditionException e) {
                    return null;
                }
            }

            @Override
            public Expression visit(Application application) {
                return null;
            }

            @Override
            public Expression visit(Variable variable) {
                return null;
            }
        }
    }
}
