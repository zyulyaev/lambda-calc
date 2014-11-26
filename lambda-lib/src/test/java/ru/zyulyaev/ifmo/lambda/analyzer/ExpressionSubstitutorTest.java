package ru.zyulyaev.ifmo.lambda.analyzer;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.BaseExpressionTest;
import ru.zyulyaev.ifmo.lambda.Variable;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

/**
 * @author zyulyaev
 * @date 26.11.14 19:37
 */
public class ExpressionSubstitutorTest extends BaseExpressionTest {
    private final ExpressionSubstitutor substitutor = new ExpressionSubstitutor(new FreeVariablesFinder());
    private void testSubstitute(String expr, String var, String subs, String result) throws ExpressionParserException, FreshnessConditionException {
        Assert.assertEquals(parse(result), substitutor.substitute(parse(expr), new Variable(var), parse(subs)));
    }

    @Test
    public void testSubstitute1() throws ExpressionParserException, FreshnessConditionException {
        testSubstitute("\\a.a b", "b", "c d", "\\a.a (c d)");
    }

    @Test
    public void testSubstitute2() throws ExpressionParserException, FreshnessConditionException {
        testSubstitute("\\a.\\b.a b c (\\d.c \\f.g) h", "c", "x y", "\\a.\\b.a b (x y) (\\d.(x y) \\f.g) h");
    }

    @Test
    public void testSubstitute3() throws ExpressionParserException, FreshnessConditionException {
        testSubstitute("\\a.\\b.a b \\c.c a b", "d", "x y", "\\a.\\b.a b \\c.c a b");
    }

    private void testSubstituteFailed(String expr, String var, String subs) throws ExpressionParserException, FreshnessConditionException {
        substitutor.substitute(parse(expr), new Variable(var), parse(subs));
    }

    @Test(expected = FreshnessConditionException.class)
    public void testSubstituteFailed1() throws ExpressionParserException, FreshnessConditionException {
        testSubstituteFailed("\\a.a b", "b", "c a");
    }

    @Test(expected = FreshnessConditionException.class)
    public void testSubstituteFailed2() throws ExpressionParserException, FreshnessConditionException {
        testSubstituteFailed("\\a.\\b.a b c (\\d.e \\f.g) h", "g", "\\x.t \\y.a t y");
    }
}
