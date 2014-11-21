package ru.zyulyaev.ifmo.lambda.parser;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author zyulyaev
 */
public class TokenizerTest {
    private void test(String expression, Token... tokens) throws IOException, UnexpectedCharacterException {
        Tokenizer tokenizer = new Tokenizer(new BufferedReader(new StringReader(expression)));
        for (Token token : tokens) {
            Assert.assertEquals(tokenizer.next(), token);
        }
        Assert.assertEquals(tokenizer.next(), Token.EOF);
    }

    private Token lit(String x) {
        return new Token(TokenType.LITERAL, x);
    }

    @Test
    public void test1() throws IOException, UnexpectedCharacterException {
        test("\\x.x", Token.LAMBDA, lit("x"), Token.DOT, lit("x"));
    }

    @Test
    public void test2() throws IOException, UnexpectedCharacterException {
        test("x x15", lit("x"), Token.WHITESPACE, lit("x15"));
    }

    @Test
    public void test3() throws IOException, UnexpectedCharacterException {
        test("\\x.\\y.y (\\a.x)",
                Token.LAMBDA,
                lit("x"),
                Token.DOT,
                Token.LAMBDA,
                lit("y"),
                Token.DOT,
                lit("y"),
                Token.WHITESPACE,
                Token.OPEN,
                Token.LAMBDA,
                lit("a"),
                Token.DOT,
                lit("x"),
                Token.CLOSE
        );
    }

    @Test
    public void test4() throws IOException, UnexpectedCharacterException {
        test("x15```\ny01",
                lit("x15```"),
                Token.WHITESPACE,
                lit("y01")
        );
    }
}
