package ru.zyulyaev.ifmo.lambda.parser;

/**
 * @author zyulyaev
 */
public class ExpressionParserException extends Exception {
    public ExpressionParserException() {
    }

    public ExpressionParserException(String message) {
        super(message);
    }

    public ExpressionParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpressionParserException(Throwable cause) {
        super(cause);
    }

    public ExpressionParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
