package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Tests model.Move class
 *
 * @author Anthony Du
 */
public class MoveTest {
    private Move move;

    @BeforeEach
    public void setup() {
        move = new Move("myMove", Type.GHOST, true);
    }

    @Test
    public void testConstructor() {
        assertEquals("myMove", move.getName());
        assertEquals(Type.GHOST, move.getType());
        assertEquals(true, move.getStatus());
    }

    @Test
    public void testToString() {
        assertEquals(
                "myMove GHOST Status",
                move.toString().replaceAll("\\s+"," ")
        );
        move.setName("myAmazingNewMove");
        move.setType(Type.WATER);
        move.setStatus(false);
        assertEquals(
                "myAmazingNewMo… WATER Attacking",
                move.toString().replaceAll("\\s+"," ")
        );
    }

    @Test
    public void testAnalyze() {

    }
}
