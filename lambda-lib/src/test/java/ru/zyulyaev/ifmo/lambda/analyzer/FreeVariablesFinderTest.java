package ru.zyulyaev.ifmo.lambda.analyzer;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.BaseExpressionTest;
import ru.zyulyaev.ifmo.lambda.Variable;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zyulyaev
 * @date 26.11.14 19:39
 */
public class FreeVariablesFinderTest extends BaseExpressionTest {
    private final FreeVariablesFinder finder = new FreeVariablesFinder();

    private void testGetFreeVariables(String expression, String... variables) throws ExpressionParserException {
        Assert.assertEquals(Stream.of(variables).map(Variable::new).collect(Collectors.toSet()), finder.findFreeVariables(parse(expression)));
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
}
