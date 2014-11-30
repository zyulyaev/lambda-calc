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
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.BufferedReaderTokenizer;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.Token;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.TokenType;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.Tokenizer;

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
        Tokenizer<IOException> tokenizer = new BufferedReaderTokenizer(in);
        while (true) {
            Tokenizer<IOException> lineTokenizer = tokenizer.terminateOn(Token.whitespace("\n")).skip(TokenType.WHITESPACE);
            SampleAlgebraicExpression left = parser.parse(lineTokenizer.terminateOn(Token.EQUALS));
            if (lineTokenizer.next() != Token.EQUALS) {
                throw new ExpressionParserException("Bad line format");
            }
            SampleAlgebraicExpression right = parser.parse(lineTokenizer);
            eqs.add(new AlgebraicEquation<>(left, right));
            if (!tokenizer.next().equals(Token.whitespace("\n"))) {
                break;
            }
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
