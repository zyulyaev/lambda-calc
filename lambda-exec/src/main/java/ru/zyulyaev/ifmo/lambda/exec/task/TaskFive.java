package ru.zyulyaev.ifmo.lambda.exec.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zyulyaev.ifmo.lambda.analyzer.SkiBuilder;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zyulyaev
 */
@Component("task5")
public class TaskFive extends BaseTaskExecutor {
    @Autowired
    SkiBuilder builder;

    @Override
    protected void execute(BufferedReader in, PrintWriter out) throws IOException, ExpressionParserException {
        out.println(builder.build(parser.parse(in)));
    }
}
