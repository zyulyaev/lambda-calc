package ru.zyulyaev.ifmo.lambda.parser;

import ru.zyulyaev.ifmo.lambda.Abstraction;
import ru.zyulyaev.ifmo.lambda.Application;
import ru.zyulyaev.ifmo.lambda.Expression;
import ru.zyulyaev.ifmo.lambda.Variable;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.Token;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.TokenType;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.Tokenizer;

import java.util.EnumSet;

/**
 * @author zyulyaev
 */
public class ExpressionParser extends BaseParser<Expression> {
    private static final EnumSet<TokenType> ATOM_START =
            EnumSet.of(TokenType.OPEN, TokenType.LITERAL, TokenType.LAMBDA);

    @Override
    protected <E extends Exception> ParserContext<Expression, E> createContext(Tokenizer<E> tokenizer) {
        return new ParserContext<Expression, E>(tokenizer.skip(TokenType.WHITESPACE)) {
            private Expression parseAtom() throws E, ExpressionParserException {
                Token token = tokenizer.next();
                switch (token.type) {
                    case OPEN:
                        Expression result = parseExpr();
                        expect(TokenType.CLOSE);
                        return result;
                    case LITERAL:
                        return new Variable(token.value);
                    case LAMBDA:
                        return parseAbstraction();
                    default:
                        throw new UnexpectedTokenException(token);
                }
            }

            private Expression parseAbstraction() throws E, ExpressionParserException {
                String var = expect(TokenType.LITERAL);
                expect(TokenType.DOT);
                return new Abstraction(new Variable(var), parseExpr());
            }

            @Override
            protected Expression parseExpr() throws E, ExpressionParserException{
                Expression app = parseAtom();
                while (ATOM_START.contains(tokenizer.peek().type)) {
                    app = new Application(app, parseAtom());
                }
                return app;
            }
        };
    }
}
