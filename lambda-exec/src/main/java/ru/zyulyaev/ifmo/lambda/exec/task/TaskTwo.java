package ru.zyulyaev.ifmo.lambda.exec.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zyulyaev.ifmo.lambda.analyzer.FreeVariablesFinder;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zyulyaev
 */
@Component("task2")
public class TaskTwo extends BaseTaskExecutor {
    @Autowired
    FreeVariablesFinder finder;

    @Override
    protected void execute(BufferedReader in, PrintWriter out) throws IOException, ExpressionParserException {
        finder.findFreeVariables(parse(in)).stream()
                .sorted((a, b) -> a.getName().compareTo(b.getName()))
                .forEach(out::println);
    }
}
