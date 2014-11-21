package ru.zyulyaev.ifmo.lambda.parser;

/**
 * @author zyulyaev
 */
public class UnexpectedTokenException extends ExpressionParserException {
    private final Token token;

    public UnexpectedTokenException(Token token) {
        this.token = token;
    }
}
