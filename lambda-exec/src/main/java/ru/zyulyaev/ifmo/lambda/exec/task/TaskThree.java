package ru.zyulyaev.ifmo.lambda.exec.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zyulyaev.ifmo.lambda.Expression;
import ru.zyulyaev.ifmo.lambda.Variable;
import ru.zyulyaev.ifmo.lambda.analyzer.ExpressionSubstitutor;
import ru.zyulyaev.ifmo.lambda.analyzer.FreshnessConditionException;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.BufferedReaderTokenizer;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.Token;
import ru.zyulyaev.ifmo.lambda.parser.tokenizer.Tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zyulyaev
 */
@Component("task3")
public class TaskThree extends BaseTaskExecutor {
    @Autowired
    ExpressionSubstitutor substitutor;

    @Override
    protected void execute(BufferedReader in, PrintWriter out) throws IOException, ExpressionParserException {
        Tokenizer<IOException> tokenizer = new BufferedReaderTokenizer(in);
        Expression origin = parser.parse(tokenizer.terminateOn(Token.BRACKET_OPEN));
        if (tokenizer.next() != Token.BRACKET_OPEN) {
            throw new ExpressionParserException("Bad format");
        }
        Expression variable = parser.parse(tokenizer.terminateOn(Token.COLON));
        if (!(variable instanceof Variable) || tokenizer.next() != Token.COLON || tokenizer.next() != Token.EQUALS) {
            throw new ExpressionParserException("Bad format");
        }
        Expression substitution = parser.parse(tokenizer.terminateOn(Token.BRACKET_CLOSE));
        if (tokenizer.next() != Token.BRACKET_CLOSE) {
            throw new ExpressionParserException("Bad format");
        }
        try {
            out.println(substitutor.substitute(origin, (Variable) variable, substitution));
        } catch (FreshnessConditionException e) {
            out.println("Нет свободы для подстановки для переменной " + e.getVariable());
        }
    }
}
