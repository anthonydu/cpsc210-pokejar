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
        // 111
        assertTrue(new Move("", Type.NORMAL, false).equals(new Move("", Type.NORMAL, false)));
        // 110
        assertFalse(new Move("", Type.NORMAL, true).equals(new Move("", Type.NORMAL, false)));
        // 101
        assertFalse(new Move("", Type.FIRE, false).equals(new Move("", Type.NORMAL, false)));
        // 100
        assertFalse(new Move("", Type.FIRE, true).equals(new Move("", Type.NORMAL, false)));
        // 011
        assertFalse(new Move("1", Type.NORMAL, false).equals(new Move("", Type.NORMAL, false)));
        // 010
        assertFalse(new Move("1", Type.NORMAL, true).equals(new Move("", Type.NORMAL, false)));
        // 001
        assertFalse(new Move("1", Type.FIRE, false).equals(new Move("", Type.NORMAL, false)));
        // 000
        assertFalse(new Move("1", Type.FIRE, true).equals(new Move("", Type.NORMAL, false)));
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
