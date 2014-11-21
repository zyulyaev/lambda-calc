package ru.zyulyaev.ifmo.lambda.parser;

import java.util.Objects;

/**
 * Created by nikita on 20.11.14.
 */
class Token {
    public static final Token LAMBDA = new Token(TokenType.LAMBDA);
    public static final Token DOT = new Token(TokenType.DOT);
    public static final Token OPEN = new Token(TokenType.OPEN);
    public static final Token CLOSE = new Token(TokenType.CLOSE);
    public static final Token WHITESPACE = new Token(TokenType.WHITESPACE);
    public static final Token EOF = new Token(TokenType.EOF);

    final TokenType type;
    final String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    private Token(TokenType type) {
        this(type, null);
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
