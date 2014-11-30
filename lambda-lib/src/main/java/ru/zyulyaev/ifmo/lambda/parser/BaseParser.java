package ru.zyulyaev.ifmo.lambda.parser;

import ru.zyulyaev.ifmo.lambda.parser.tokenizer.Token;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.TokenType;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.Tokenizer;

/**
 * Created by nikita on 22.11.14.
 */
public abstract class BaseParser<T> {
    protected abstract <E extends Exception> ParserContext<T, E> createContext(Tokenizer<E> tokenizer);

    public <E extends Exception> T parse(Tokenizer<E> tokenizer) throws E, ExpressionParserException {
        ParserContext<T, E> context = createContext(tokenizer);
        T result = context.parseExpr();
        context.expect(TokenType.EOF);
        return result;
    }

    protected abstract static class ParserContext<T, E extends Exception> {
        protected final Tokenizer<E> tokenizer;

        protected ParserContext(Tokenizer<E> tokenizer) {
            this.tokenizer = tokenizer;
        }

        protected String expect(TokenType type) throws E, ExpressionParserException {
            Token token = tokenizer.next();
            if (token.type != type) {
                throw new UnexpectedTokenException(token);
            }
            return token.value;
        }

        protected boolean consume(TokenType type) throws E, ExpressionParserException {
            if (tokenizer.peek().type == type) {
                tokenizer.next();
                return true;
            }
            return false;
        }

        protected abstract T parseExpr() throws E, ExpressionParserException;
    }
}
