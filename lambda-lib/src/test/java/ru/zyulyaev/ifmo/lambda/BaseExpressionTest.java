package ru.zyulyaev.ifmo.lambda;

import ru.zyulyaev.ifmo.lambda.parser.ExpressionParser;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

/**
 * Created by nikita on 24.11.14.
 */
abstract class BaseExpressionTest {
    private final ExpressionParser parser = new ExpressionParser();

    Expression parse(String expr) throws ExpressionParserException {
        return parser.parse(expr);
    }
}
