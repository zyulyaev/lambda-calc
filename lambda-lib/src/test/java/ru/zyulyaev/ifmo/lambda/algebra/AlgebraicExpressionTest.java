package ru.zyulyaev.ifmo.lambda.algebra;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.BaseSampleAlgebraicExpressionTest;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicVariable;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

/**
 * Created by nikita on 22.11.14.
 */
public class AlgebraicExpressionTest extends BaseSampleAlgebraicExpressionTest {
    private void testSubstitute(String before, String var, String subs, String after) throws ExpressionParserException {
        Assert.assertEquals(parse(after), parse(before).substitute(new SampleAlgebraicVariable(var), parse(subs)));
    }

    @Test
    public void testSubstitute1() throws ExpressionParserException {
        testSubstitute("x", "x", "f(y)", "f(y)");
    }

    @Test
    public void testSubstitute2() throws ExpressionParserException {
        testSubstitute("x", "y", "f(y)", "x");
    }

    @Test
    public void testSubstitute3() throws ExpressionParserException {
        testSubstitute("f(x,y,g(x))", "x", "h(z,g(z))", "f(h(z,g(z)),y,g(h(z,g(z))))");
    }

    @Test
    public void testIsVariable() throws ExpressionParserException {
        Assert.assertTrue(parse("x").isVariable());
        Assert.assertFalse(parse("f(x)").isVariable());
    }

    @Test
    public void testIsFunction() throws ExpressionParserException {
        Assert.assertTrue(parse("f(x)").isFunction());
        Assert.assertFalse(parse("x").isFunction());
    }
}
