package ru.zyulyaev.ifmo.lambda.parser.tokenizer;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author zyulyaev
 */
public class TokenizerTest {
    private void test(String expression, Token... tokens) throws UnexpectedCharacterException {
        Tokenizer<RuntimeException> tokenizer = BufferedReaderTokenizer.stringTokenizer(expression);
        for (Token token : tokens) {
            Assert.assertEquals(token, tokenizer.next());
        }
        Assert.assertEquals(Token.EOF, tokenizer.next());
    }

    private Token lit(String x) {
        return Token.literal(x);
    }

    private Token ws(String w) {
        return Token.whitespace(w);
    }

    @Test
    public void test1() throws UnexpectedCharacterException {
        test("\\x.x", Token.LAMBDA, lit("x"), Token.DOT, lit("x"));
    }

    @Test
    public void test2() throws UnexpectedCharacterException {
        test("x x15", lit("x"), ws(" "), lit("x15"));
    }

    @Test
    public void test3() throws UnexpectedCharacterException {
        test("\\x.\\y.y (\\a.x)",
                Token.LAMBDA,
                lit("x"),
                Token.DOT,
                Token.LAMBDA,
                lit("y"),
                Token.DOT,
                lit("y"),
                ws(" "),
                Token.OPEN,
                Token.LAMBDA,
                lit("a"),
                Token.DOT,
                lit("x"),
                Token.CLOSE
        );
    }

    @Test
    public void test4() throws UnexpectedCharacterException {
        test("x15```\ny01",
                lit("x15```"),
                ws("\n"),
                lit("y01")
        );
    }

    @Test
    public void test5() throws UnexpectedCharacterException {
        test("f(x,y,g(z))",
                lit("f"),
                Token.OPEN,
                lit("x"),
                Token.COMMA,
                lit("y"),
                Token.COMMA,
                lit("g"),
                Token.OPEN,
                lit("z"),
                Token.CLOSE,
                Token.CLOSE
        );
    }
}
