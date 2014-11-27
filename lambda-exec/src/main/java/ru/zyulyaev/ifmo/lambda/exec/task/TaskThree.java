package ru.zyulyaev.ifmo.lambda.exec.task;

import org.springframework.stereotype.Component;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zyulyaev
 */
@Component("task3")
public class TaskThree extends BaseTaskExecutor {
    @Override
    protected void execute(BufferedReader in, PrintWriter out) throws IOException, ExpressionParserException {
        // todo
    }
}
