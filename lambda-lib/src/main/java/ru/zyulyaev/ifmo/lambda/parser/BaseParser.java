package ru.zyulyaev.ifmo.lambda.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by nikita on 22.11.14.
 */
abstract class BaseParser<T> {
    String expect(Tokenizer tokenizer, TokenType type) throws IOException, ExpressionParserException {
        Token token = tokenizer.next();
        if (token.type != type) {
            throw new UnexpectedTokenException(token);
        }
        return token.value;
    }

    boolean consume(Tokenizer tokenizer, TokenType type) throws IOException, ExpressionParserException {
        if (tokenizer.peek().type == type) {
            tokenizer.next();
            return true;
        }
        return false;
    }

    abstract T parse(Tokenizer tokenizer) throws IOException, ExpressionParserException;

    public T parse(BufferedReader reader) throws IOException, ExpressionParserException {
        Tokenizer tokenizer = new Tokenizer(reader);
        T result = parse(tokenizer);
        expect(tokenizer, TokenType.EOF);
        return result;
    }

    public T parse(String expression) throws ExpressionParserException {
        try {
            return parse(new BufferedReader(new StringReader(expression)));
        } catch (IOException exception) {
            throw new RuntimeException("Unexpected exception", exception);
        }
    }
}
