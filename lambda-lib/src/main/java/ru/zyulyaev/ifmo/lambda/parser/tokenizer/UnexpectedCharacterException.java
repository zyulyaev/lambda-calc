package ru.zyulyaev.ifmo.lambda.parser.tokenizer;

import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

/**
 * Created by nikita on 20.11.14.
 */
public class UnexpectedCharacterException extends ExpressionParserException {
    private final char character;

    public UnexpectedCharacterException(char character) {
        super("Unexpected '" + character + "'");
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }
}
