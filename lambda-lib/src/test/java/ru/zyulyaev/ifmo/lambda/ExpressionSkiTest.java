package ru.zyulyaev.ifmo.lambda;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

/**
 * Created by nikita on 24.11.14.
 */
public class ExpressionSkiTest extends BaseExpressionTest {
    private void testToSki(String before, String after) throws ExpressionParserException {
        Assert.assertEquals(after, parse(before).accept(StandardExpressionVisitor.SKI_CONVERTER).toString());
    }

    @Test
    public void testToSki1() throws ExpressionParserException {
        testToSki("\\x.\\y.y x", "((S (K (S I))) ((S (K K)) I))");
    }
}
