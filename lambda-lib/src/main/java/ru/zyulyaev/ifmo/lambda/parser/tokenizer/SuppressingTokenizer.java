package ru.zyulyaev.ifmo.lambda.parser.tokenizer;

/**
 * Created by nikita on 30.11.14.
 */
class SuppressingTokenizer implements Tokenizer<RuntimeException> {
    private final Tokenizer<?> tokenizer;

    SuppressingTokenizer(Tokenizer<?> tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public Token next() throws UnexpectedCharacterException {
        try {
            return tokenizer.next();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Token peek() throws UnexpectedCharacterException {
        try {
            return tokenizer.peek();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
