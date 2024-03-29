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
    Pokemon tinkaton2 = new Pokemon(tinkaton.getName(), tinkaton.getTypes(), tinkaton.getMoves());

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
        assertEquals(rotom, box.get(rotom));
    }

    @Test
    public void testAdd() {
        assertTrue(box.add(tinkaton));
    }

    @Test
    public void testRemove() {
        assertEquals(rotom, box.remove(1));
        assertEquals(2, box.size());
        assertEquals(tinkaton, box.get(0));
        assertEquals(cetitan, box.get(1));
        assertTrue(box.remove(cetitan));
        assertEquals(1, box.size());
        assertEquals(tinkaton, box.get(0));
        assertFalse(box.remove(rotom));
        assertEquals(1, box.size());
        assertEquals(tinkaton, box.get(0));
    }

    @Test
    public void testToString() {
        assertEquals(
                "0 Tinkaton        FAIRY       STEEL\n" +
                "1 Rotom           ELECTRIC    WATER\n" +
                "2 Cetitan         ICE",
                box.toString()
        );
    }
}
