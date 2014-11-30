package ru.zyulyaev.ifmo.lambda.parser.tokenizer;

import java.util.Set;

/**
 * Created by nikita on 30.11.14.
 */
class TerminatingTokenizer<E extends Exception> implements Tokenizer<E> {
    private final Tokenizer<E> tokenizer;
    private final Set<Token> terminators;

    TerminatingTokenizer(Tokenizer<E> tokenizer, Set<Token> terminators) {
        this.tokenizer = tokenizer;
        this.terminators = terminators;
    }

    @Override
    public Token next() throws E, UnexpectedCharacterException {
        if (terminators.contains(tokenizer.peek())) {
            return Token.EOF;
        }
        return tokenizer.next();
    }

    @Override
    public Token peek() throws E, UnexpectedCharacterException {
        if (terminators.contains(tokenizer.peek())) {
            return Token.EOF;
        }
        return tokenizer.peek();
    }
}
