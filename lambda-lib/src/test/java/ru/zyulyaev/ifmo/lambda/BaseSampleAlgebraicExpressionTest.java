package ru.zyulyaev.ifmo.lambda;

import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicExpression;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;
import ru.zyulyaev.ifmo.lambda.parser.SampleAlgebraicExpressionParser;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.BufferedReaderTokenizer;

/**
 * Created by nikita on 30.11.14.
 */
public class BaseSampleAlgebraicExpressionTest {
    private final SampleAlgebraicExpressionParser parser = new SampleAlgebraicExpressionParser();

    protected SampleAlgebraicExpression parse(String expr) throws ExpressionParserException {
        return parser.parse(BufferedReaderTokenizer.stringTokenizer(expr));
    }
}
