package ru.zyulyaev.ifmo.lambda.analyzer;

import ru.zyulyaev.ifmo.lambda.Expression;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zyulyaev
 * @date 26.11.14 18:14
 */
public abstract class CachedExpressionAnalyzer<T> {
    private final Map<Expression, T> cache = new HashMap<>();

    protected CachedExpressionAnalyzer(int maxCacheSize) { // ignore argument for now
        // todo use maxCacheSize
    }

    protected CachedExpressionAnalyzer() {
        this(Integer.MAX_VALUE);
    }

    protected T getOrCompute(Expression expression) {
        return cache.computeIfAbsent(expression, this::compute);
    }

    protected abstract T compute(Expression expression);
}
