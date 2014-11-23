package ru.zyulyaev.ifmo.lambda;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

/**
 * Created by nikita on 24.11.14.
 */
public class ExpressionBeautifyTest extends BaseExpressionTest {
    private void testBeautify(String before, String after) throws ExpressionParserException {
        Assert.assertEquals(parse(after), parse(before).accept(new ExpressionBeautifier()));
    }

    @Test
    public void testBeautify1() throws ExpressionParserException {
        testBeautify("x", "a");
    }

    @Test
    public void testBeautify2() throws ExpressionParserException {
        testBeautify("\\x.x", "\\a.a");
    }

    @Test
    public void testBeautify3() throws ExpressionParserException {
        testBeautify("\\x.y", "\\a.b");
    }

    @Test
    public void testBeautify4() throws ExpressionParserException {
        testBeautify("x y", "a b");
    }

    @Test
    public void testBeautify5() throws ExpressionParserException {
        testBeautify("x x", "a a");
    }

    @Test
    public void testBeautify6() throws ExpressionParserException {
        testBeautify("x x \\y.y x", "a a \\b.b a");
    }

    @Test
    public void testBeautify7() throws ExpressionParserException {
        testBeautify("(\\y.y x) x x", "(\\a.a b) b b");
    }

    @Test
    public void testBeautify8() throws ExpressionParserException {
        testBeautify("\\x.x (\\x.x \\x.x y) \\y.a y", "\\a.a (\\b.b \\c.c d) \\e.f e");
    }

    @Test
    public void testBeautify9() throws ExpressionParserException {
        testBeautify("(\\x.\\y.y x) x y", "(\\a.\\b.b a) c d");
    }
}
