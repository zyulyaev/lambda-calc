package ru.zyulyaev.ifmo.lambda;

import java.util.Optional;

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
            String variable = abstraction.getVariable();
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
            private final String variable;

            private AbstractionSkiVisitor(String variable) {
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
            String variable = abstraction.getVariable();
            Expression expression = abstraction.getExpression();

            Expression normalized = expression.accept(NORMALIZER);
            return normalized == expression ? abstraction : new Abstraction(variable, normalized);
        }

        @Override
        public Expression visit(Application application) {
            final Expression leftNormalized = application.getLeft().accept(NORMALIZER);
            final Expression rightNormalized = application.getRight().accept(NORMALIZER);
            return leftNormalized.accept(new ApplicationNormalizeVisitor(rightNormalized))
                    .orElseGet(() -> new Application(leftNormalized, rightNormalized));
        }

        @Override
        public Expression visit(Variable variable) {
            return variable;
        }

        class ApplicationNormalizeVisitor implements ExpressionVisitor<Optional<Expression>> {
            private final Expression rightNormalized;

            public ApplicationNormalizeVisitor(Expression rightNormalized) {
                this.rightNormalized = rightNormalized;
            }

            @Override
            public Optional<Expression> visit(Abstraction abstraction) {
                try {
                    return Optional.of(abstraction.getExpression().substitute(abstraction.getVariable(), rightNormalized));
                } catch (FreshnessConditionException e) {
                    return Optional.empty();
                }
            }

            @Override
            public Optional<Expression> visit(Application application) {
                return Optional.empty();
            }

            @Override
            public Optional<Expression> visit(Variable variable) {
                return Optional.empty();
            }
        }
    }
}
