package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestSubjects;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests model.Box class
 *
 * @author Anthony Du
 */
public class BoxTest extends TestSubjects {
    private Box box;

    @BeforeEach
    public void setup() {
        box = new Box();
        box.add(tinkaton);
        box.add(rotom);
        box.add(cetitan);
    }

    @Test
    public void testConstructor() {
        assertEquals(Arrays.asList(tinkaton, rotom, cetitan), box);
    }

    @Test
    public void testGet() {
        assertEquals(tinkaton, box.get(0));
        assertEquals(rotom, box.get(1));
        assertEquals(cetitan, box.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> box.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> box.get(3));
    }

    @Test
    public void testAdd() {
        assertThrows(IllegalArgumentException.class, () -> box.add(rotom));
    }

    @Test
    public void testRemove() {
        box.remove(cetitan);
        assertEquals(Arrays.asList(tinkaton, rotom), box);
        box.remove(tinkaton);
        assertEquals(Arrays.asList(rotom), box);
    }

    @Test
    public void testToString() {
        assertEquals(
                "0 Tinkaton        FAIRY           STEEL\n" +
                "1 Rotom           ELECTRIC        WATER\n" +
                "2 Cetitan         ICE",
                box.toString()
        );
    }
}
