package ru.zyulyaev.ifmo.lambda.exec.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;
import ru.zyulyaev.ifmo.lambda.types.ExpressionTypeRecognizer;
import ru.zyulyaev.ifmo.lambda.types.LambdaTypeWithContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * @author zyulyaev
 */
@Component("task7")
public class TaskSeven extends BaseTaskExecutor {
    @Autowired
    ExpressionTypeRecognizer recognizer;

    @Override
    protected void execute(BufferedReader in, PrintWriter out) throws IOException, ExpressionParserException {
        Optional<LambdaTypeWithContext> type = recognizer.recognizeSimpleType(parser.parse(in));
        if (type.isPresent()) {
            out.println(type.get());
        } else {
            out.println("Лямбда-выражение не имеет типа.");
        }
    }
}
