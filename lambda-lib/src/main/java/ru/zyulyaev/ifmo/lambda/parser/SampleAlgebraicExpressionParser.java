package ru.zyulyaev.ifmo.lambda.parser;

import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicExpression;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicFunction;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikita on 22.11.14.
 */
public class SampleAlgebraicExpressionParser extends BaseParser<SampleAlgebraicExpression> {
    @Override
    SampleAlgebraicExpression parse(Tokenizer tokenizer) throws IOException, ExpressionParserException {
        String lit = expect(tokenizer, TokenType.LITERAL);
        if (lit.charAt(0) <= 'h') {
            expect(tokenizer, TokenType.OPEN);
            List<SampleAlgebraicExpression> arguments = new ArrayList<>();
            do {
                arguments.add(parse(tokenizer));
            } while (consume(tokenizer, TokenType.COMMA));
            expect(tokenizer, TokenType.CLOSE);
            return new SampleAlgebraicFunction(lit, arguments);
        } else {
            return new SampleAlgebraicVariable(lit);
        }
    }
}
