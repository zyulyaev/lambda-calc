package ru.zyulyaev.ifmo.lambda.analyzer;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.BaseExpressionTest;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

/**
 * Created by nikita on 24.11.14.
 */
public class ExpressionNormalizeTest extends BaseExpressionTest {
    private final ExpressionNormalizer normalizer = new ExpressionNormalizer(new ExpressionSubstitutor(new FreeVariablesFinder()));

    private void testNormalize(String denorm, String norm) throws ExpressionParserException {
        Assert.assertEquals(parse(norm), normalizer.normalize(parse(denorm)));
    }

    @Test
    public void testNormalize1() throws ExpressionParserException {
        testNormalize("(\\a.a) b", "b");
    }

    @Test
    public void testNormalize2() throws ExpressionParserException {
        testNormalize("(\\a.a) (\\c.d) q", "d");
    }

    @Test
    public void testNormalize3() throws ExpressionParserException {
        testNormalize("\\a.\\b.a b c (\\d.e \\f.g) h", "\\a.\\b.a b c (\\d.e \\f.g) h");
    }

    @Test
    public void testNormalize4() throws ExpressionParserException {
        testNormalize("(\\a.\\b.a) x ((\\x.x x) \\x.x x)", "x");
    }

    @Test
    public void testNormalize5() throws ExpressionParserException {
        testNormalize("(\\x.x y) \\a.a", "y");
    }
}
