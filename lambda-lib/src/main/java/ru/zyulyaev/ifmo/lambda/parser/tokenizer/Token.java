package ru.zyulyaev.ifmo.lambda.parser.tokenizer;

import java.util.Objects;

/**
 * Created by nikita on 20.11.14.
 */
public class Token {
    public static final Token LAMBDA = new Token(TokenType.LAMBDA);
    public static final Token DOT = new Token(TokenType.DOT);
    public static final Token COMMA = new Token(TokenType.COMMA);
    public static final Token OPEN = new Token(TokenType.OPEN);
    public static final Token CLOSE = new Token(TokenType.CLOSE);
    public static final Token EQUALS = new Token(TokenType.EQUALS);
    public static final Token BRACKET_OPEN = new Token(TokenType.BRACKET_OPEN);
    public static final Token BRACKET_CLOSE = new Token(TokenType.BRACKET_CLOSE);
    public static final Token COLON = new Token(TokenType.COLON);
    public static final Token EOF = new Token(TokenType.EOF);

    public final TokenType type;
    public final String value;

    private Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    private Token(TokenType type) {
        this(type, null);
    }

    public static Token literal(String value) {
        return new Token(TokenType.LITERAL, value);
    }

    public static Token whitespace(String value) {
        return new Token(TokenType.WHITESPACE, value);
    }

    @Override
    public String toString() {
        return type + (value == null ? "" : "[" + value + "]");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;
        return type == token.type && Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return 31 * (value != null ? value.hashCode() : 0) + type.hashCode();
    }
}
