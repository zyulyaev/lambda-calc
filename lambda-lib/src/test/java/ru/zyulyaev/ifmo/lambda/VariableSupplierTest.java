package ru.zyulyaev.ifmo.lambda;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Supplier;

/**
 * Created by nikita on 24.11.14.
 */
public class VariableSupplierTest {
    @Test
    public void testConstruct() {
        new VariableSupplier("q");
        new VariableSupplier("z0");
        new VariableSupplier("a1");
        new VariableSupplier("z192");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructFailed1() {
        new VariableSupplier("z01");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructFailed2() {
        new VariableSupplier("xy");
    }

    @Test
    public void testGetSequence() {
        Supplier<Variable> supplier = new VariableSupplier();
        for (int i = 0; i < 'z' - 'a' + 1; ++i) {
            Assert.assertEquals(Character.toString((char) ('a' + i)), supplier.get().getName());
        }
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 'z' - 'a' + 1; j++) {
                Assert.assertEquals(((char) ('a' + j)) + "" + i, supplier.get().getName());
            }
        }
        Assert.assertEquals("a100", supplier.get().getName());
    }

    @Test
    public void testGetUncommonStart() {
        Supplier<Variable> supplier = new VariableSupplier("x101");
        Assert.assertEquals("x101", supplier.get().getName());
        Assert.assertEquals("y101", supplier.get().getName());
        Assert.assertEquals("z101", supplier.get().getName());
        Assert.assertEquals("a102", supplier.get().getName());
    }
}
