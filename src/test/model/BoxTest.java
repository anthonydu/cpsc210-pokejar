package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestSubjects;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests model.Box class.
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
        Pokemon tinkaton2 = new Pokemon(
                tinkaton.getName(), Arrays.asList(Type.GRASS),
                Arrays.asList(new Move("", Type.WATER, false))
        );
        assertThrows(IllegalArgumentException.class, () -> new Box(Arrays.asList(tinkaton, tinkaton2)));
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
        assertFalse(box.add(tinkaton));
    }

    @Test
    public void testIndexOf() {
        assertEquals(1, box.indexOf("Rotom"));
        assertEquals(-1, box.indexOf("rotom"));
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
