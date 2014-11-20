package ru.zyulyaev.ifmo.lambda.parser;

/**
 * Created by nikita on 20.11.14.
 */
class Token {
    public static final Token LAMBDA = new Token(TokenType.LAMBDA);
    public static final Token DOT = new Token(TokenType.DOT);
    public static final Token OPEN = new Token(TokenType.OPEN);
    public static final Token CLOSE = new Token(TokenType.CLOSE);
    public static final Token WHITESPACE = new Token(TokenType.WHITESPACE);

    final TokenType type;
    final String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    private Token(TokenType type) {
        this(type, null);
    }
}
