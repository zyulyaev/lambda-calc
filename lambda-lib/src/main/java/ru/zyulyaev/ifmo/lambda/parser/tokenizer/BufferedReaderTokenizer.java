package ru.zyulyaev.ifmo.lambda.parser.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by nikita on 30.11.14.
 */
public class BufferedReaderTokenizer implements Tokenizer<IOException> {
    private final BufferedReader reader;
    private int lastChar = Integer.MIN_VALUE;
    private Token lastToken;

    public BufferedReaderTokenizer(BufferedReader reader) {
        this.reader = reader;
    }

    public static Tokenizer<RuntimeException> stringTokenizer(String s) {
        return new BufferedReaderTokenizer(new BufferedReader(new StringReader(s))).suppressExceptions();
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
        if (lastToken == Token.EOF) return lastToken;
        int c = nextChar();
        switch (c) {
            case '\\': return Token.LAMBDA;
            case '.': return Token.DOT;
            case ',': return Token.COMMA;
            case '(': return Token.OPEN;
            case ')': return Token.CLOSE;
            case '=': return Token.EQUALS;
            case '[': return Token.BRACKET_OPEN;
            case ']': return Token.BRACKET_CLOSE;
            case ':': return Token.COLON;
            case ' ':
            case '\r':
            case '\n':
            case '\t':
                return Token.whitespace(Character.toString((char) c));
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
                return Token.literal(builder.toString());
        }
    }

    @Override
    public Token peek() throws IOException, UnexpectedCharacterException {
        ensureFirstToken();
        return lastToken;
    }

    @Override
    public Token next() throws IOException, UnexpectedCharacterException {
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
