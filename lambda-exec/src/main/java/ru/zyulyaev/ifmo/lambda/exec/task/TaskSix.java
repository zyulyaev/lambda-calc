package ru.zyulyaev.ifmo.lambda.exec.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicEquation;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicSystem;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicExpression;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicFunction;
import ru.zyulyaev.ifmo.lambda.algebra.sample.SampleAlgebraicVariable;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;
import ru.zyulyaev.ifmo.lambda.parser.SampleAlgebraicExpressionParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zyulyaev
 */
@Component("task6")
public class TaskSix extends BaseTaskExecutor {
    @Autowired
    SampleAlgebraicExpressionParser parser;

    @Override
    protected void execute(BufferedReader in, PrintWriter out) throws IOException, ExpressionParserException {
        List<AlgebraicEquation<SampleAlgebraicVariable, SampleAlgebraicFunction, SampleAlgebraicExpression>> eqs = new ArrayList<>();
        for (String line = in.readLine(); line != null; line = in.readLine()) {
            String[] s = line.split("=", 2);
            eqs.add(new AlgebraicEquation<>(parser.parse(s[0]), parser.parse(s[1])));
        }
        AlgebraicSystem<SampleAlgebraicVariable, SampleAlgebraicFunction, SampleAlgebraicExpression> system =
                new AlgebraicSystem<>(eqs).trySolve();
        if (system.isSolved()) {
            out.println(system);
        } else {
            out.println("Cannot be solved");
        }
    }
}
