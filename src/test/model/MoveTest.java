package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static model.Type.GHOST;
import static model.Type.WATER;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests model.Move class.
 *
 * @author Anthony Du
 */
public class MoveTest {
    private Move move;

    @BeforeEach
    public void setup() {
        move = new Move("myMove", GHOST, true);
    }

    @Test
    public void testConstructor() {
        assertEquals("myMove", move.getName());
        assertEquals(GHOST, move.getType());
        assertEquals(true, move.isStatus());
    }

    @Test
    public void testEquals() {
        assertTrue(new Pokemon().equals(new Pokemon()));
        assertFalse(
                new Pokemon("", new ArrayList<>(), new ArrayList<>())
                        .equals(new Pokemon("1", new ArrayList<>(), new ArrayList<>()))
        );
        assertFalse(
                new Pokemon("", new ArrayList<>(), new ArrayList<>())
                        .equals(new Pokemon("1", new ArrayList<>(Arrays.asList(Type.NORMAL)), new ArrayList<>()))
        );
        assertFalse(
                new Pokemon("", new ArrayList<>(), new ArrayList<>(Arrays.asList(new Move("", Type.NORMAL, false))))
                        .equals(new Pokemon("1", new ArrayList<>(Arrays.asList(Type.NORMAL)), new ArrayList<>()))
        );
        assertFalse(
                new Pokemon("", new ArrayList<>(), new ArrayList<>(Arrays.asList(new Move("", Type.NORMAL, false))))
                        .equals(new Pokemon("1", new ArrayList<>(), new ArrayList<>()))
        );
        assertFalse(
                new Pokemon("", new ArrayList<>(), new ArrayList<>(Arrays.asList(new Move("", Type.NORMAL, false))))
                        .equals(new Pokemon("", new ArrayList<>(Arrays.asList(Type.NORMAL)), new ArrayList<>()))
        );
    }

    @Test
    public void testToString() {
        assertEquals(
                "myMove GHOST Status",
                move.toString().replaceAll("\\s+"," ")
        );
        move.setName("myAmazingNewMove");
        move.setType(WATER);
        move.setStatus(false);
        assertEquals(
                "myAmazingNewMo… WATER Attacking",
                move.toString().replaceAll("\\s+"," ")
        );
    }
}
