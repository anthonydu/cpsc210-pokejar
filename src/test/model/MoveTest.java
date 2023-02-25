package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Type.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
