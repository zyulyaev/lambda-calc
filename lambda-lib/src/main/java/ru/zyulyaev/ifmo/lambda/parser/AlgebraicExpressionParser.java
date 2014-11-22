package ru.zyulyaev.ifmo.lambda.parser;

import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicExpression;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicFunction;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikita on 22.11.14.
 */
public class AlgebraicExpressionParser extends BaseParser<AlgebraicExpression> {
    @Override
    AlgebraicExpression parse(Tokenizer tokenizer) throws IOException, ExpressionParserException {
        String lit = expect(tokenizer, TokenType.LITERAL);
        if (lit.charAt(0) <= 'h') {
            expect(tokenizer, TokenType.OPEN);
            List<AlgebraicExpression> arguments = new ArrayList<>();
            do {
                arguments.add(parse(tokenizer));
            } while (consume(tokenizer, TokenType.COMMA));
            expect(tokenizer, TokenType.CLOSE);
            return new AlgebraicFunction(lit, arguments);
        } else {
            return new AlgebraicVariable(lit);
        }
    }
}
