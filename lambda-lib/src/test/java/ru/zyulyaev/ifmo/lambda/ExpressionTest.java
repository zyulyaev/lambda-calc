package ru.zyulyaev.ifmo.lambda;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

/**
 * @author zyulyaev
 */
public class ExpressionTest extends BaseExpressionTest {
   private void testToString(String before, String after) throws ExpressionParserException {
        Assert.assertEquals(after, parse(before).toString());
    }

    @Test
    public void testToString1() throws ExpressionParserException {
        testToString("\\a.a b", "(\\a.(a b))");
    }

    @Test
    public void testToString2() throws ExpressionParserException {
        testToString("\\a.\\b.a b c (\\d.e \\f.g) h", "(\\a.(\\b.((((a b) c) (\\d.(e (\\f.g)))) h)))");
    }

    @Test
    public void testToString3() throws ExpressionParserException {
        testToString("\\a.\\b.a b \\c.c a b", "(\\a.(\\b.((a b) (\\c.((c a) b)))))");
    }
}
