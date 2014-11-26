package ru.zyulyaev.ifmo.lambda.analyzer;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.BaseExpressionTest;
import ru.zyulyaev.ifmo.lambda.analyzer.FreeVariablesFinder;
import ru.zyulyaev.ifmo.lambda.analyzer.SkiBuilder;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

/**
 * Created by nikita on 24.11.14.
 */
public class ExpressionSkiTest extends BaseExpressionTest {
    private final SkiBuilder builder = new SkiBuilder(new FreeVariablesFinder());

    private void testToSki(String before, String after) throws ExpressionParserException {
        Assert.assertEquals(after, builder.build(parse(before)).toString());
    }

    @Test
    public void testToSki1() throws ExpressionParserException {
        testToSki("\\x.\\y.y x", "((S (K (S I))) ((S (K K)) I))");
    }
}
