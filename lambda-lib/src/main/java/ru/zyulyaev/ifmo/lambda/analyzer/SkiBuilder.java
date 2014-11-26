package ru.zyulyaev.ifmo.lambda.analyzer;

import ru.zyulyaev.ifmo.lambda.*;

/**
 * @author zyulyaev
 * @date 26.11.14 19:09
 */
public class SkiBuilder extends CachedExpressionAnalyzer<Expression> {
    private final FreeVariablesFinder finder;

    public SkiBuilder(FreeVariablesFinder finder, int maxCacheSize) {
        super(maxCacheSize);
        this.finder = finder;
    }

    public SkiBuilder(FreeVariablesFinder finder) {
        this.finder = finder;
    }

    public Expression build(Expression expression) {
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
            if (finder.findFreeVariables(expression).contains(variable)) {
                return expression.accept(new AbstractionSkiVisitor(variable));
            } else {
                return new Application(new Variable("K"), build(expression));
            }
        }

        @Override
        public Expression visit(Application application) {
            return new Application(build(application.getLeft()), build(application.getRight()));
        }

        @Override
        public Expression visit(Variable variable) {
            return variable;
        }
    };

    private class AbstractionSkiVisitor implements ExpressionVisitor<Expression> {
        private final Variable variable;

        private AbstractionSkiVisitor(Variable variable) {
            this.variable = variable;
        }

        @Override
        public Expression visit(Abstraction abstraction) {
            return build(new Abstraction(variable, build(abstraction)));
        }

        @Override
        public Expression visit(Application application) {
            return new Application(
                    new Application(
                            new Variable("S"),
                            build(new Abstraction(variable, application.getLeft()))
                    ),
                    build(new Abstraction(variable, application.getRight()))
            );
        }

        @Override
        public Expression visit(Variable variable) {
            return new Variable("I");
        }
    }
}
