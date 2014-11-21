package ru.zyulyaev.ifmo.lambda.parser;

import ru.zyulyaev.ifmo.lambda.Abstraction;
import ru.zyulyaev.ifmo.lambda.Application;
import ru.zyulyaev.ifmo.lambda.Expression;
import ru.zyulyaev.ifmo.lambda.Variable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author zyulyaev
 */
public class ExpressionParser {
    private String expect(Tokenizer tokenizer, TokenType type) throws IOException, ExpressionParserException {
        Token token = tokenizer.next();
        if (token.type != type) {
            throw new UnexpectedTokenException(token);
        }
        return token.value;
    }

    private Expression parseAtom(Tokenizer tokenizer) throws IOException, ExpressionParserException {
        Token token = tokenizer.next();
        switch (token.type) {
            case OPEN:
                Expression result = parseApplication(tokenizer);
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
        return new Abstraction(var, parseApplication(tokenizer));
    }

    private Expression parseApplication(Tokenizer tokenizer) throws IOException, ExpressionParserException {
        Expression app = parseAtom(tokenizer);
        while (tokenizer.peek().type == TokenType.WHITESPACE) {
            tokenizer.next();
            app = new Application(app, parseAtom(tokenizer));
        }
        return app;
    }

    public Expression parse(BufferedReader reader) throws IOException, ExpressionParserException {
        Tokenizer tokenizer = new Tokenizer(reader);
        Expression result = parseApplication(tokenizer);
        expect(tokenizer, TokenType.EOF);
        return result;
    }

    public Expression parse(String expression) throws ExpressionParserException {
        try {
            return parse(new BufferedReader(new StringReader(expression)));
        } catch (IOException exception) {
            throw new RuntimeException("Unexpected exception", exception);
        }
    }
}
