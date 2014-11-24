package ru.zyulyaev.ifmo.lambda.parser;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicExpression;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicFunction;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicVariable;

import java.util.Arrays;

/**
 * Created by nikita on 22.11.14.
 */
public class SampleAlgebraicExpressionParserTest {
    private final SampleAlgebraicExpressionParser parser = new SampleAlgebraicExpressionParser();

    private void test(String expression, SampleAlgebraicExpression valid) throws ExpressionParserException {
        Assert.assertEquals(valid, parser.parse(expression));
    }

    private SampleAlgebraicFunction fun(String name, SampleAlgebraicExpression... args) {
        return new SampleAlgebraicFunction(name, Arrays.asList(args));
    }

    private SampleAlgebraicVariable var(String var) {
        return new SampleAlgebraicVariable(var);
    }

    @Test
    public void test1() throws ExpressionParserException {
        test("x", var("x"));
    }

    @Test
    public void test2() throws ExpressionParserException {
        test("f(x)", fun("f", var("x")));
    }

    @Test
    public void test3() throws ExpressionParserException {
        test("f(x,y,g(z))", fun("f", var("x"), var("y"), fun("g", var("z"))));
    }

    @Test
    public void test4() throws ExpressionParserException {
        test("f(g(x),y,g(z,h(a(q),t)))",
                fun("f",
                        fun("g", var("x")),
                        var("y"),
                        fun("g",
                                var("z"),
                                fun("h",
                                        fun("a", var("q")),
                                        var("t")
                                )
                        )
                )
        );
    }
}
