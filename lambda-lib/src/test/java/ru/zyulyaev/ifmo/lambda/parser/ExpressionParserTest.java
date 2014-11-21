package ru.zyulyaev.ifmo.lambda.parser;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.Abstraction;
import ru.zyulyaev.ifmo.lambda.Application;
import ru.zyulyaev.ifmo.lambda.Expression;
import ru.zyulyaev.ifmo.lambda.Variable;

/**
 * @author zyulyaev
 */
public class ExpressionParserTest {
    private final ExpressionParser parser = new ExpressionParser();

    private void test(String expression, Expression valid) throws ExpressionParserException {
        Assert.assertEquals(parser.parse(expression), valid);
    }

    private Expression abs(String var, Expression expr) {
        return new Abstraction(var, expr);
    }

    private Expression app(Expression left, Expression right) {
        return new Application(left, right);
    }

    private Expression var(String var) {
        return new Variable(var);
    }

    @Test
    public void test1() throws ExpressionParserException {
        test("\\x.x", abs("x", var("x")));
    }

    @Test
    public void test2() throws ExpressionParserException {
        test("\\a.\\b.a b c (\\d.e \\f.g) h",
                abs("a",
                        abs("b",
                                app(
                                        app(
                                                app(
                                                        app(var("a"), var("b")),
                                                        var("c")
                                                ),
                                                abs("d",
                                                        app(
                                                                var("e"),
                                                                abs("f",
                                                                        var("g")
                                                                )
                                                        )
                                                )
                                        ),
                                        var("h")
                                )
                        )
                )
        );
    }
}