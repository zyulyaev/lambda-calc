package ru.zyulyaev.ifmo.lambda.types;

import ru.zyulyaev.ifmo.lambda.*;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicEquation;
import ru.zyulyaev.ifmo.lambda.algebra.AlgebraicSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author zyulyaev
 * @date 24.11.14 18:36
 */
public class ExpressionTypeRecognizer {
    public Optional<LambdaTypeWithContext> recognizeSimpleType(Expression expression) {
        Expression beautiful = expression.accept(new ExpressionBeautifier());
        Supplier<String> nameSupplier = new VariableNameSupplier(beautiful.accept(LAST_VARIABLE_NAME_FINDER));
        nameSupplier.get(); // skip one
        DataCollector collector = new DataCollector(nameSupplier);
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

    private static final ExpressionVisitor<String> LAST_VARIABLE_NAME_FINDER = new ExpressionVisitor<String>() {
        private String max(String a, String b) {
            if (a.length() != b.length()) return a.length() > b.length() ? a : b;
            String aSubs = a.substring(1);
            String bSubs = b.substring(1);
            if (a.length() == 1 || Integer.parseInt(aSubs) == Integer.parseInt(bSubs)) return a.charAt(0) > b.charAt(0) ? a : b;
            return Integer.parseInt(aSubs) > Integer.parseInt(bSubs) ? a : b;
        }

        @Override
        public String visit(Abstraction abstraction) {
            return max(abstraction.getVariable(), abstraction.getExpression().accept(this));
        }

        @Override
        public String visit(Application application) {
            return max(application.getLeft().accept(this), application.getRight().accept(this));
        }

        @Override
        public String visit(Variable variable) {
            return variable.getName();
        }
    };

    private static class DataCollector implements ExpressionVisitor<LambdaType> {
        final List<AlgebraicEquation<LambdaTypeVariable, LambdaTypeApplication, LambdaType>> equations = new ArrayList<>();
        private final Supplier<String> typeNameSupplier;

        private DataCollector(Supplier<String> typeNameSupplier) {
            this.typeNameSupplier = typeNameSupplier;
        }

        @Override
        public LambdaType visit(Abstraction abstraction) {
            return new LambdaTypeApplication(new LambdaTypeVariable(abstraction.getVariable()), abstraction.getExpression().accept(this));
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
