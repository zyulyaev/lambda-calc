package ru.zyulyaev.ifmo.lambda.types;

import org.junit.Assert;
import org.junit.Test;
import ru.zyulyaev.ifmo.lambda.BaseExpressionTest;
import ru.zyulyaev.ifmo.lambda.analyzer.ExpressionBeautifier;
import ru.zyulyaev.ifmo.lambda.analyzer.FreeVariablesFinder;
import ru.zyulyaev.ifmo.lambda.analyzer.VariablesFinder;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParserException;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author zyulyaev
 * @date 24.11.14 18:54
 */
public class ExpressionTypeRecognizerTest extends BaseExpressionTest {
    private final ExpressionTypeRecognizer recognizer = new ExpressionTypeRecognizer(new ExpressionBeautifier(new FreeVariablesFinder()), new VariablesFinder());

    private Optional<LambdaTypeWithContext> recognize(String expr) throws ExpressionParserException {
        return recognizer.recognizeSimpleType(parse(expr));
    }

    private LambdaTypeVariable var(String name) {
        return new LambdaTypeVariable(name);
    }

    private LambdaTypeApplication app(LambdaType left, LambdaType right) {
        return new LambdaTypeApplication(left, right);
    }

    private LambdaTypeWithContext.Mapping map(String from, LambdaType to) {
        return new LambdaTypeWithContext.Mapping(new LambdaTypeVariable(from), to);
    }

    private LambdaTypeWithContext ctx(LambdaType type, LambdaTypeWithContext.Mapping... mappings) {
        return new LambdaTypeWithContext(type, Arrays.asList(mappings));
    }

    private void testRecognizeSimple(String expr, LambdaTypeWithContext expected) throws ExpressionParserException {
        if (expected == null) {
            Assert.assertTrue(!recognize(expr).isPresent());
        } else {
            Assert.assertEquals(expected, recognize(expr).get());
        }
    }

    @Test
    public void testRecognizeSimple1() throws ExpressionParserException {
        testRecognizeSimple("\\x.x", ctx(app(var("a"), var("a"))));
    }

    @Test
    public void testRecognizeSimple2() throws ExpressionParserException {
        testRecognizeSimple("x y", ctx(var("a"), map("x", app(var("y"), var("a")))));
    }

    @Test
    public void testRecognizeSimple3() throws ExpressionParserException {
        testRecognizeSimple("(\\x.x x) (\\x.x x)", null);
    }
}
