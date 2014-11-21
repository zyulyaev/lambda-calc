package ru.zyulyaev.ifmo.lambda.parser;

/**
 * Created by nikita on 20.11.14.
 */
public class UnexpectedCharacterException extends ExpressionParserException {
    private final char c;

    public UnexpectedCharacterException(char c) {
        this.c = c;
    }
}
