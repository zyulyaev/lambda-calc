package ru.zyulyaev.ifmo.lambda;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

/**
 * Created by nikita on 24.11.14.
 */
public class ExpressionNormalizeTest extends BaseExpressionTest {
    private void testNormalize(String denorm, String norm) throws ExpressionParserException {
        Assert.assertEquals(parse(norm), parse(denorm).accept(StandardExpressionVisitor.NORMALIZER));
    }

    @Test
    public void testNormalize1() throws ExpressionParserException {
        testNormalize("(\\a.a) b", "b");
    }

    @Test
    public void testNormalize2() throws ExpressionParserException {
        testNormalize("(\\a.a a) (\\a.a a)", "(\\a.a a) (\\a.a a)");
    }

    @Test
    public void testNormalize3() throws ExpressionParserException {
        testNormalize("(\\a.a) (\\c.d) q", "d");
    }

    @Test
    public void testNormalize4() throws ExpressionParserException {
        testNormalize("\\a.\\b.a b c (\\d.e \\f.g) h", "\\a.\\b.a b c (\\d.e \\f.g) h");
    }

    @Test
    public void testNormalize5() throws ExpressionParserException {
        testNormalize("(\\a.\\b.a) x ((\\x.x x) \\x.x x)", "x");
    }

    @Test
    public void testNormalize6() throws ExpressionParserException {
        testNormalize("(\\x.x y) \\a.a", "y");
    }
}
