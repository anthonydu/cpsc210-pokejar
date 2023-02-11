package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests model.Box class.
 *
 * @author Anthony Du
 */
public class BoxTest {
    private Pokemon tinkaton = new Pokemon(
            "Tinkaton",
            new ArrayList<>(List.of(Type.FAIRY, Type.STEEL)),
            new ArrayList<>(List.of(
                    new Move("Gigaton Hammer", Type.STEEL, false),
                    new Move("Play Rough", Type.FAIRY, false),
                    new Move("Swords Dance", Type.NORMAL, true),
                    new Move("Encore", Type.NORMAL, true)
            ))
    );
    private Pokemon rotom = new Pokemon(
            "Rotom",
            new ArrayList<>(List.of(Type.ELECTRIC, Type.WATER)),
            new ArrayList<>(List.of(
                    new Move("Hydro Pump", Type.WATER, false),
                    new Move("Thunderbolt", Type.ELECTRIC, false),
                    new Move("Hex", Type.GHOST, false),
                    new Move("Nasty Plot", Type.DARK, true)

            ))
    );
    private Pokemon cetitan = new Pokemon(
            "Cetitan",
            new ArrayList<>(List.of(Type.ICE)),
            new ArrayList<>(List.of(
                    new Move("Avalanche", Type.ICE, false),
                    new Move("Ice Shard", Type.ICE, false),
                    new Move("Earthquake", Type.GROUND, false),
                    new Move("Heavy Slam", Type.STEEL, false)

            ))
    );

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
        assertEquals(List.of(tinkaton, rotom, cetitan), box.get());
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
        assertEquals(List.of(tinkaton, rotom), box.get());
        box.remove(tinkaton);
        assertEquals(List.of(rotom), box.get());
    }

    @Test
    public void testToString() {
        assertEquals(
                "0 Tinkaton FAIRY STEEL 1 Rotom ELECTRIC WATER 2 Cetitan ICE",
                box.toString().replaceAll("\\s+"," ")
        );
    }
}
