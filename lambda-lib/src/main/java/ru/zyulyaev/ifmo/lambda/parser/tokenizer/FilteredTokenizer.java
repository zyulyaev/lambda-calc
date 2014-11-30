package ru.zyulyaev.ifmo.lambda.parser.tokenizer;

import java.util.Set;

/**
 * Created by nikita on 30.11.14.
 */
class FilteredTokenizer<E extends Exception> implements Tokenizer<E> {
    private final Tokenizer<E> tokenizer;
    private final Set<TokenType> skipTypes;

    FilteredTokenizer(Tokenizer<E> tokenizer, Set<TokenType> skipTypes) {
        this.tokenizer = tokenizer;
        this.skipTypes = skipTypes;
    }

    private void skip() throws E, UnexpectedCharacterException {
        while (skipTypes.contains(tokenizer.peek().type)) {
            tokenizer.next();
        }
    }

    @Override
    public Token next() throws E, UnexpectedCharacterException {
        skip();
        return tokenizer.next();
    }

    @Override
    public Token peek() throws E, UnexpectedCharacterException {
        skip();
        return tokenizer.peek();
    }
}
