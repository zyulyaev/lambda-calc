package ru.zyulyaev.ifmo.lambda.exec.task;

import org.springframework.beans.factory.annotation.Autowired;
import ru.zyulyaev.ifmo.lambda.Expression;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParser;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.BufferedReaderTokenizer;

import java.io.*;

/**
 * @author zyulyaev
 */
public abstract class BaseTaskExecutor implements TaskExecutor {
    @Autowired
    ExpressionParser parser;

    protected Expression parse(BufferedReader in) throws IOException, ExpressionParserException {
        return parser.parse(new BufferedReaderTokenizer(in));
    }

    protected abstract void execute(BufferedReader in, PrintWriter out) throws IOException, ExpressionParserException;

    @Override
    public final void execute(InputStream in, OutputStream out) {
        try (PrintWriter writer = new PrintWriter(out)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                execute(reader, writer);
            } catch (IOException e) {
                e.printStackTrace();
                writer.println("Error while reading");
            } catch (ExpressionParserException e) {
                e.printStackTrace();
                writer.println("Bad expression (" + e + ")");
            }
        }
    }
}
