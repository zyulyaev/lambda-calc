package ru.zyulyaev.ifmo.lambda;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Supplier;

/**
 * Created by nikita on 24.11.14.
 */
public class VariableNameSupplierTest {
    @Test
    public void testGetSequence() {
        Supplier<String> supplier = new VariableNameSupplier();
        for (int i = 0; i < 'z' - 'a' + 1; ++i) {
            Assert.assertEquals(Character.toString((char) ('a' + i)), supplier.get());
        }
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 'z' - 'a' + 1; j++) {
                Assert.assertEquals(((char) ('a' + j)) + "" + i, supplier.get());
            }
        }
        Assert.assertEquals("a100", supplier.get());
    }

    @Test
    public void testGetAvoiding() {
        Supplier<String> supplier = new VariableNameSupplier(new HashSet<>(Arrays.asList(
                "a", "b", "d", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
        )));
        Assert.assertEquals("c", supplier.get());
        Assert.assertEquals("e", supplier.get());
        Assert.assertEquals("f", supplier.get());
        Assert.assertEquals("a0", supplier.get());
    }
}
