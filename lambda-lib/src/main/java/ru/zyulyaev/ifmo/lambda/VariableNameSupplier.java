package ru.zyulyaev.ifmo.lambda;

import java.util.function.Supplier;

/**
 * Created by nikita on 24.11.14.
 */
public class VariableNameSupplier implements Supplier<String> {
    private final StringBuilder next;

    public VariableNameSupplier(String first) {
        if (!first.matches("[a-z](0|[1-9][0-9]*)?")) {
            throw new IllegalArgumentException("Illegal first variable name " + first);
        }
        this.next = new StringBuilder(first);
    }

    public VariableNameSupplier() {
        this("a");
    }

    @Override
    public String get() {
        String result = next.toString();
        prepareNext();
        return result;
    }

    private void prepareNext() {
        if (next.charAt(0) == 'z') {
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
    }

    @Override
    public String toString() {
        return "next=" + next;
    }
}