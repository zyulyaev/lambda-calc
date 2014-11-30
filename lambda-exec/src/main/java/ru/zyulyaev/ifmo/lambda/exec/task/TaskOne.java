package ru.zyulyaev.ifmo.lambda.exec.task;

import org.springframework.stereotype.Component;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zyulyaev
 */
@Component("task1")
public class TaskOne extends BaseTaskExecutor {
    @Override
    protected void execute(BufferedReader in, PrintWriter out) throws IOException, ExpressionParserException {
        out.println(parse(in));
    }
}
