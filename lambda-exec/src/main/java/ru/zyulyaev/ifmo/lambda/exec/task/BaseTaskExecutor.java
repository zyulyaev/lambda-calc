package ru.zyulyaev.ifmo.lambda.exec.task;

import org.springframework.beans.factory.annotation.Autowired;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParser;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

import java.io.*;

/**
 * @author zyulyaev
 */
public abstract class BaseTaskExecutor implements TaskExecutor {
    @Autowired
    ExpressionParser parser;

    protected abstract void execute(BufferedReader in, PrintWriter out) throws IOException, ExpressionParserException;

    @Override
    public final void execute(InputStream in, OutputStream out) {
        try (PrintWriter writer = new PrintWriter(out)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                execute(reader, writer);
            } catch (IOException e) {
                writer.println("Error while reading");
            } catch (ExpressionParserException e) {
                writer.println("Bad expression (" + e + ")");
            }
        }
    }
}
