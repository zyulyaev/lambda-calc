package ru.zyulyaev.ifmo.lambda;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Supplier;

/**
 * Created by nikita on 24.11.14.
 */
public class VariableNameSupplierTest {
    @Test
    public void testConstruct() {
        new VariableNameSupplier("q");
        new VariableNameSupplier("z0");
        new VariableNameSupplier("a1");
        new VariableNameSupplier("z192");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructFailed1() {
        new VariableNameSupplier("z01");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructFailed2() {
        new VariableNameSupplier("xy");
    }

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
    public void testGetUncommonStart() {
        Supplier<String> supplier = new VariableNameSupplier("x101");
        Assert.assertEquals("x101", supplier.get());
        Assert.assertEquals("y101", supplier.get());
        Assert.assertEquals("z101", supplier.get());
        Assert.assertEquals("a102", supplier.get());
    }
}
