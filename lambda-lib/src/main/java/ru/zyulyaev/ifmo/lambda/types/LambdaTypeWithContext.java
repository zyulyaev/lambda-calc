package ru.zyulyaev.ifmo.lambda.types;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zyulyaev
 * @date 24.11.14 19:40
 */
public class LambdaTypeWithContext {
    private final LambdaType type;
    private final List<Mapping> mappings;

    public LambdaTypeWithContext(LambdaType type, List<Mapping> mappings) {
        this.type = type;
        this.mappings = Collections.unmodifiableList(mappings);
    }

    public LambdaType getType() {
        return type;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LambdaTypeWithContext that = (LambdaTypeWithContext) o;
        return type.equals(that.type) && mappings.equals(that.mappings);

    }

    @Override
    public int hashCode() {
        return 31 * type.hashCode() + mappings.hashCode();
    }

    @Override
    public String toString() {
        return type + mappings.stream().map(x -> "\n" + x).collect(Collectors.joining());
    }

    public static class Mapping {
        private final LambdaTypeVariable from;
        private final LambdaType to;

        public Mapping(LambdaTypeVariable from, LambdaType to) {
            this.from = from;
            this.to = to;
        }

        public LambdaTypeVariable getFrom() {
            return from;
        }

        public LambdaType getTo() {
            return to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Mapping mapping = (Mapping) o;
            return from.equals(mapping.from) && to.equals(mapping.to);
        }

        @Override
        public int hashCode() {
            return 31 * from.hashCode() + to.hashCode();
        }

        @Override
        public String toString() {
            return from + "=" + to;
        }
    }
}
