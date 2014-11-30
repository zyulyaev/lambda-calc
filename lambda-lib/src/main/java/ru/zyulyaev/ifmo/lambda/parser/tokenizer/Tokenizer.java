package ru.zyulyaev.ifmo.lambda.parser.tokenizer;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nikita on 20.11.14.
 */
public interface Tokenizer<E extends Exception> {
    Token next() throws E, UnexpectedCharacterException;

    Token peek() throws E, UnexpectedCharacterException;

    default Tokenizer<E> terminateOn(Set<Token> tokens) {
        return new TerminatingTokenizer<>(this, tokens);
    }

    default Tokenizer<E> terminateOn(Token... tokens) {
        Set<Token> set = new HashSet<>();
        Collections.addAll(set, tokens);
        return terminateOn(set);
    }

    default Tokenizer<E> skip(Set<TokenType> types) {
        return new FilteredTokenizer<>(this, types);
    }

    default Tokenizer<E> skip(TokenType... types) {
        Set<TokenType> set = new HashSet<>();
        Collections.addAll(set, types);
        return skip(EnumSet.copyOf(set));
    }

    default Tokenizer<RuntimeException> suppressExceptions() {
        return new SuppressingTokenizer(this);
    }
}