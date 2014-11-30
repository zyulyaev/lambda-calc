package ru.zyulyaev.ifmo.lambda.parser;

import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicExpression;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicFunction;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicVariable;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.TokenType;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikita on 22.11.14.
 */
public class SampleAlgebraicExpressionParser extends BaseParser<SampleAlgebraicExpression> {
    @Override
    protected <E extends Exception> ParserContext<SampleAlgebraicExpression, E> createContext(Tokenizer<E> tokenizer) {
        return new ParserContext<SampleAlgebraicExpression, E>(tokenizer.skip(TokenType.WHITESPACE)) {
            @Override
            protected SampleAlgebraicExpression parseExpr() throws E, ExpressionParserException {
                String lit = expect(TokenType.LITERAL);
                if (lit.charAt(0) <= 'h') {
                    expect(TokenType.OPEN);
                    List<SampleAlgebraicExpression> arguments = new ArrayList<>();
                    do {
                        arguments.add(parseExpr());
                    } while (consume(TokenType.COMMA));
                    expect(TokenType.CLOSE);
                    return new SampleAlgebraicFunction(lit, arguments);
                } else {
                    return new SampleAlgebraicVariable(lit);
                }
            }
        };
    }
}
