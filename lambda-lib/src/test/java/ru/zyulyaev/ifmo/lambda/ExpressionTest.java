package ru.zyulyaev.ifmo.lambda;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParser;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zyulyaev
 */
public class ExpressionTest {
    private final ExpressionParser parser = new ExpressionParser();

    private Expression parse(String expr) throws ExpressionParserException {
        return parser.parse(expr);
    }

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

    private void testGetFreeVariables(String expression, String... variables) throws ExpressionParserException {
        Set<String> set = new HashSet<>();
        Collections.addAll(set, variables);
        Assert.assertEquals(set, parse(expression).getFreeVariables());
    }

    @Test
    public void testGetFreeVariables1() throws ExpressionParserException {
        testGetFreeVariables("\\a.a b", "b");
    }

    @Test
    public void testGetFreeVariables2() throws ExpressionParserException {
        testGetFreeVariables("\\a.\\b.a b c (\\d.e \\f.g) h", "c", "e", "g", "h");
    }

    @Test
    public void testGetFreeVariables3() throws ExpressionParserException {
        testGetFreeVariables("\\a.\\b.a b \\c.c a b");
    }

    private void testSubstitute(String expr, String var, String subs, String result) throws ExpressionParserException, FreshnessConditionException {
        Assert.assertEquals(parse(result), parse(expr).substitute(var, parse(subs)));
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
        parse(expr).substitute(var, parse(subs));
    }

    @Test(expected = FreshnessConditionException.class)
    public void testSubstituteFailed1() throws ExpressionParserException, FreshnessConditionException {
        testSubstituteFailed("\\a.a b", "b", "c a");
    }

    @Test(expected = FreshnessConditionException.class)
    public void testSubstituteFailed2() throws ExpressionParserException, FreshnessConditionException {
        testSubstituteFailed("\\a.\\b.a b c (\\d.e \\f.g) h", "g", "\\x.t \\y.a t y");
    }

    private void testNormalize(String denorm, String norm) throws ExpressionParserException {
        Assert.assertEquals(parse(norm), parse(denorm).normalize());
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

    private void testToSki(String before, String after) throws ExpressionParserException {
        Assert.assertEquals(after, parse(before).toSki().toString());
    }

    @Test
    public void testToSki1() throws ExpressionParserException {
        testToSki("\\x.\\y.y x", "((S (K (S I))) ((S (K K)) I))");
    }
}
