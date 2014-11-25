package ru.zyulyaev.ifmo.lambda;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by nikita on 24.11.14.
 */
public class VariableNameSupplier implements Supplier<String> {
    private final StringBuilder next = new StringBuilder();
    private final Set<String> avoid;

    public VariableNameSupplier(Set<String> avoid) {
        this.avoid = new HashSet<>(avoid);
        prepareNext();
    }

    public VariableNameSupplier() {
        this(Collections.emptySet());
    }

    @Override
    public String get() {
        String result = next.toString();
        prepareNext();
        return result;
    }

    private void prepareNext() {
        do {
            if (next.length() == 0) {
                next.append('a');
            } else if (next.charAt(0) == 'z') {
                next.setCharAt(0, 'a');
                int len = next.length();
                if (len == 1) {
                    next.append(0);
                } else {
                    while (len > 1 && next.charAt(len - 1) == '9') {
                        next.setCharAt(--len, '0');
                    }
                    if (len == 1) {
                        next.append(0);
                        next.setCharAt(1, '1');
                    } else {
                        next.setCharAt(len - 1, (char) (next.charAt(len - 1) + 1));
                    }
                }
            } else {
                next.setCharAt(0, (char) (next.charAt(0) + 1));
            }
        } while (avoid.contains(next.toString()));
    }

    @Override
    public String toString() {
        return "next=" + next;
    }
}
