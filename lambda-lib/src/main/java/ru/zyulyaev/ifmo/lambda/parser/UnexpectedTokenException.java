package ru.zyulyaev.ifmo.lambda.parser;

import ru.zyulyaev.ifmo.lambda.parser.tokenizer.Token;

/**
 * @author zyulyaev
 */
public class UnexpectedTokenException extends ExpressionParserException {
    private final Token token;

    public UnexpectedTokenException(Token token) {
        super("Unexpected " + token);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
