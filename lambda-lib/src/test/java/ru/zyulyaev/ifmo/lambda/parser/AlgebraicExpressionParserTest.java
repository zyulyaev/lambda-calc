package ru.zyulyaev.ifmo.lambda.parser;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicExpression;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicFunction;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicVariable;

import java.util.Arrays;

/**
 * Created by nikita on 22.11.14.
 */
public class AlgebraicExpressionParserTest {
    private final AlgebraicExpressionParser parser = new AlgebraicExpressionParser();

    private void test(String expression, AlgebraicExpression valid) throws ExpressionParserException {
        Assert.assertEquals(valid, parser.parse(expression));
    }

    private AlgebraicExpression fun(String name, AlgebraicExpression... args) {
        return new AlgebraicFunction(name, Arrays.asList(args));
    }

    private AlgebraicExpression var(String var) {
        return new AlgebraicVariable(var);
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
