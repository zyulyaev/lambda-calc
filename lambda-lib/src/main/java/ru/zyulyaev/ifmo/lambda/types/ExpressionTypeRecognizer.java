package ru.zyulyaev.ifmo.lambda.types;

import ru.zyulyaev.ifmo.lambda.*;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicEquation;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicSystem;
import ru.zyulyaev.ifmo.lambda.analyzer.ExpressionBeautifier;
import ru.zyulyaev.ifmo.lambda.analyzer.VariablesFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author zyulyaev
 * @date 24.11.14 18:36
 */
public class ExpressionTypeRecognizer {
    private final ExpressionBeautifier beautifier;
    private final VariablesFinder finder;

    public ExpressionTypeRecognizer(ExpressionBeautifier beautifier, VariablesFinder finder) {
        this.beautifier = beautifier;
        this.finder = finder;
    }

    public Optional<LambdaTypeWithContext> recognizeSimpleType(Expression expression) {
        Expression beautiful = beautifier.beautify(expression);
        Set<String> avoid = finder.findVariables(beautiful).stream().map(Variable::getName).collect(Collectors.toSet());
        DataCollector collector = new DataCollector(new VariableNameSupplier(avoid));
        LambdaType type = beautiful.accept(collector);
        AlgebraicSystem<LambdaTypeVariable, LambdaTypeApplication, LambdaType> system = new AlgebraicSystem<>(collector.equations).trySolve();
        if (system.isSolved()) {
            return Optional.of(new LambdaTypeWithContext(
                    type,
                    system.getEquations().stream()
                            .map(eq -> new LambdaTypeWithContext.Mapping(eq.getLeft().asVariable(), eq.getRight()))
                            .collect(Collectors.toList())
            ));
        } else {
            return Optional.empty();
        }
    }

    private static class DataCollector implements ExpressionVisitor<LambdaType> {
        final List<AlgebraicEquation<LambdaTypeVariable, LambdaTypeApplication, LambdaType>> equations = new ArrayList<>();
        private final Supplier<String> typeNameSupplier;

        private DataCollector(Supplier<String> typeNameSupplier) {
            this.typeNameSupplier = typeNameSupplier;
        }

        @Override
        public LambdaType visit(Abstraction abstraction) {
            return new LambdaTypeApplication(new LambdaTypeVariable(abstraction.getVariable().getName()), abstraction.getExpression().accept(this));
        }

        @Override
        public LambdaType visit(Application application) {
            LambdaType left = application.getLeft().accept(this);
            LambdaType right = application.getRight().accept(this);
            LambdaType type = new LambdaTypeVariable(typeNameSupplier.get());
            equations.add(new AlgebraicEquation<>(left, new LambdaTypeApplication(right, type)));
            return type;
        }

        @Override
        public LambdaType visit(Variable variable) {
            return new LambdaTypeVariable(variable.getName());
        }
    }
}
