package ru.zyulyaev.ifmo.lambda.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Created by nikita on 20.11.14.
 */
class BufferedReaderIterator {
    private final BufferedReader reader;
    private int lastChar;

    BufferedReaderIterator(BufferedReader reader) throws IOException {
        this.reader = reader;
        nextChar();
    }

    private void nextChar() throws IOException {
        this.lastChar = reader.read();
    }

    boolean hasNext() {
        return lastChar != -1;
    }

    char poll() throws IOException {
        checkHasNext();
        char result = (char) lastChar;
        nextChar();
        return result;
    }

    char peek() {
        checkHasNext();
        return (char) lastChar;
    }

    private void checkHasNext() {
        if (!hasNext()) throw new NoSuchElementException();
    }
}
