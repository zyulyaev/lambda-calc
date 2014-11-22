package ru.zyulyaev.ifmo.lambda.parser;

import ru.zyulyaev.ifmo.lambda.Abstraction;
import ru.zyulyaev.ifmo.lambda.Application;
import ru.zyulyaev.ifmo.lambda.Expression;
import ru.zyulyaev.ifmo.lambda.Variable;

import java.io.IOException;

/**
 * @author zyulyaev
 */
public class ExpressionParser extends BaseParser<Expression> {
    private Expression parseAtom(Tokenizer tokenizer) throws IOException, ExpressionParserException {
        Token token = tokenizer.next();
        switch (token.type) {
            case OPEN:
                Expression result = parse(tokenizer);
                expect(tokenizer, TokenType.CLOSE);
                return result;
            case LITERAL:
                return new Variable(token.value);
            case LAMBDA:
                return parseAbstraction(tokenizer);
            default:
                throw new UnexpectedTokenException(token);
        }
    }

    private Expression parseAbstraction(Tokenizer tokenizer) throws IOException, ExpressionParserException {
        String var = expect(tokenizer, TokenType.LITERAL);
        expect(tokenizer, TokenType.DOT);
        return new Abstraction(var, parse(tokenizer));
    }

    @Override
    Expression parse(Tokenizer tokenizer) throws IOException, ExpressionParserException {
        Expression app = parseAtom(tokenizer);
        while (consume(tokenizer, TokenType.WHITESPACE)) {
            app = new Application(app, parseAtom(tokenizer));
        }
        return app;
    }
}
