package ru.zyulyaev.ifmo.lambda.algebra;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicExpression;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicFunction;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicVariable;
import ru.zyulyaev.ifmo.lambda.parser.SampleAlgebraicExpressionParser;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikita on 22.11.14.
 */
public class AlgebraicSystemTest {
    private final SampleAlgebraicExpressionParser parser = new SampleAlgebraicExpressionParser();

    private SampleAlgebraicExpression parse(String expr) throws ExpressionParserException {
        return parser.parse(expr);
    }

    private AlgebraicSystem<SampleAlgebraicVariable, SampleAlgebraicFunction, SampleAlgebraicExpression> sys(String... eqs) throws ExpressionParserException {
        List<AlgebraicEquation<SampleAlgebraicVariable, SampleAlgebraicFunction, SampleAlgebraicExpression>> equations = new ArrayList<>();
        for (String e : eqs) {
            String[] parts = e.split("=");
            equations.add(new AlgebraicEquation<>(parse(parts[0]), parse(parts[1])));
        }
        return new AlgebraicSystem<>(equations);
    }

    private void testSolve(String... eqs) throws ExpressionParserException {
        Assert.assertTrue(sys(eqs).trySolve().isSolved());
    }

    @Rule
    public Timeout timeout = new Timeout(100);

    @Test
    public void testSolve1() throws ExpressionParserException {
        testSolve(
                "f(x,y)=f(y,x)",
                "g(y)=g(h(z))"
        );
    }

    private void testInconsistent(String... eqs) throws ExpressionParserException {
        Assert.assertTrue(sys(eqs).trySolve().isInconsistent());
    }

    @Test
    public void testInconsistent1() throws ExpressionParserException {
        testInconsistent(
                "f(x,y)=f(y,x)",
                "g(y)=g(h(x))"
        );
    }
}
