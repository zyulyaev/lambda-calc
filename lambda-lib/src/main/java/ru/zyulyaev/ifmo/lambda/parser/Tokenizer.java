package ru.zyulyaev.ifmo.lambda.parser;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by nikita on 20.11.14.
 */
class Tokenizer {
    private final BufferedReader reader;
    private int lastChar = Integer.MIN_VALUE;
    private Token lastToken;

    Tokenizer(BufferedReader reader) {
        this.reader = reader;
    }

    private int nextChar() throws IOException {
        ensureFirstChar();
        if (lastChar == -1) return lastChar;
        int c = lastChar;
        lastChar = reader.read();
        return c;
    }

    private int peekChar() throws IOException {
        ensureFirstChar();
        return lastChar;
    }

    private void ensureFirstChar() throws IOException {
        if (lastChar == Integer.MIN_VALUE) {
            lastChar = reader.read();
        }
    }

    private Token readToken() throws IOException, UnexpectedCharacterException {
        int c = nextChar();
        switch (c) {
            case '\\': return Token.LAMBDA;
            case '.': return Token.DOT;
            case '(': return Token.OPEN;
            case ')': return Token.CLOSE;
            case ' ':
            case '\r':
            case '\n':
            case '\t':
                return Token.WHITESPACE;
            case -1:
                return Token.EOF;
            default:
                if (c < 'a' || c > 'z') throw new UnexpectedCharacterException((char) c);
                StringBuilder builder = new StringBuilder(String.valueOf((char) c));
                while (peekChar() >= '0' && peekChar() <= '9') {
                    builder.append((char) nextChar());
                }
                while (peekChar() == '`') {
                    builder.append((char) nextChar());
                }
                return new Token(TokenType.LITERAL, builder.toString());
        }
    }

    Token peek() throws IOException, UnexpectedCharacterException {
        ensureFirstToken();
        return lastToken;
    }

    Token next() throws IOException, UnexpectedCharacterException {
        ensureFirstToken();
        Token result = lastToken;
        lastToken = readToken();
        return result;
    }

    private void ensureFirstToken() throws IOException, UnexpectedCharacterException {
        if (lastToken == null) {
            lastToken = readToken();
        }
    }
}
