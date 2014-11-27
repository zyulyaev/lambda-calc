package ru.zyulyaev.ifmo.lambda.exec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.zyulyaev.ifmo.lambda.analyzer.*;
import ru.zyulyaev.ifmo.lambda.parser.ExpressionParser;
import ru.zyulyaev.ifmo.lambda.parser.SampleAlgebraicExpressionParser;
import ru.zyulyaev.ifmo.lambda.types.ExpressionTypeRecognizer;

/**
 * @author zyulyaev
 */
@Configuration
@Import(TaskExecutorConfig.class)
public class MainConfig {
    @Bean
    public ExpressionParser expressionParser() {
        return new ExpressionParser();
    }

    @Bean
    public SampleAlgebraicExpressionParser sampleAlgebraicExpressionParser() {
        return new SampleAlgebraicExpressionParser();
    }

    @Bean
    public VariablesFinder variablesFinder() {
        return new VariablesFinder();
    }

    @Bean
    public FreeVariablesFinder freeVariablesFinder() {
        return new FreeVariablesFinder();
    }

    @Bean
    public ExpressionSubstitutor expressionSubstitutor() {
        return new ExpressionSubstitutor(freeVariablesFinder());
    }

    @Bean
    public ExpressionNormalizer expressionNormalizer() {
        return new ExpressionNormalizer(expressionSubstitutor());
    }

    @Bean
    public SkiBuilder skiBuilder() {
        return new SkiBuilder(freeVariablesFinder());
    }

    @Bean
    public ExpressionBeautifier expressionBeautifier() {
        return new ExpressionBeautifier(freeVariablesFinder());
    }

    @Bean
    public ExpressionTypeRecognizer expressionTypeRecognizer() {
        return new ExpressionTypeRecognizer(expressionBeautifier(), variablesFinder());
    }
}
