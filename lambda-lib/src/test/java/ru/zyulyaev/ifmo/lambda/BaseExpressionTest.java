package ru.zyulyaev.ifmo.lambda;

import ru.zyulyaev.ifmo.lambda.parser.ExpressionParser;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.BufferedReaderTokenizer;

/**
 * Created by nikita on 24.11.14.
 */
public abstract class BaseExpressionTest {
    private final ExpressionParser parser = new ExpressionParser();

    protected Expression parse(String expr) throws ExpressionParserException {
        return parser.parse(BufferedReaderTokenizer.stringTokenizer(expr));
    }
}
