package ru.zyulyaev.ifmo.lambda.parser;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by nikita on 20.11.14.
 */
class Tokenizer {
    private final BufferedReaderIterator iterator;

    Tokenizer(BufferedReader reader) throws IOException {
        this.iterator = new BufferedReaderIterator(reader);
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Token next() throws IOException, UnexpectedCharacterException {
        char c = iterator.poll();
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
            default:
//                if (c < 'a' || c > 'z') throw new
        }
    }
}
