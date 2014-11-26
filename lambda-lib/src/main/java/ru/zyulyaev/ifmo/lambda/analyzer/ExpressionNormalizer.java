package ru.zyulyaev.ifmo.lambda.analyzer;

import ru.zyulyaev.ifmo.lambda.*;

/**
 * @author zyulyaev
 * @date 26.11.14 18:29
 */
public class ExpressionNormalizer extends CachedExpressionAnalyzer<Expression> {
    private final ExpressionSubstitutor substitutor;

    public ExpressionNormalizer(ExpressionSubstitutor substitutor, int maxCacheSize) {
        super(maxCacheSize);
        this.substitutor = substitutor;
    }

    public ExpressionNormalizer(ExpressionSubstitutor substitutor) {
        this.substitutor = substitutor;
    }

    /**
     * @return equivalent lambda expression with all possible beta-reductions done
     */
    public Expression normalize(Expression expression) {
        return getOrCompute(expression);
    }

    @Override
    protected Expression compute(Expression expression) {
        return expression.accept(visitor);
    }

    private final ExpressionVisitor<Expression> visitor = new ExpressionVisitor<Expression>() {
        @Override
        public Expression visit(Abstraction abstraction) {
            Variable variable = abstraction.getVariable();
            Expression expression = abstraction.getExpression();

            Expression normalized = normalize(expression);
            return normalized == expression ? abstraction : new Abstraction(variable, normalized);
        }

        @Override
        public Expression visit(Application application) {
            Expression redex = application.getLeft().accept(new RedexVisitor(application.getRight()));
            if (redex == null) {
                Expression left = normalize(application.getLeft());
                Expression right = application.getRight();
                if (left == application.getLeft()) {
                    right = normalize(right);
                }
                if (left == application.getLeft() && right == application.getRight()) {
                    return application;
                }
                return normalize(new Application(left, right));
            } else {
                return normalize(redex);
            }
        }

        @Override
        public Expression visit(Variable variable) {
            return variable;
        }
    };

    private class RedexVisitor implements ExpressionVisitor<Expression> {
        private final Expression rightExpression;

        RedexVisitor(Expression rightExpression) {
            this.rightExpression = rightExpression;
        }

        @Override
        public Expression visit(Abstraction abstraction) {
            try {
                return substitutor.substitute(abstraction.getExpression(), abstraction.getVariable(), rightExpression);
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
