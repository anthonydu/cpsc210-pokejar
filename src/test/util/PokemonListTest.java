package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import model.Move;
import model.Pokemon;
import model.Type;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests model.Box class.
 *
 * @author Anthony Du
 */
public class PokemonListTest {
    protected Pokemon tinkaton = new Pokemon(
            "Tinkaton",
            new ArrayList<>(Arrays.asList(Type.FAIRY, Type.STEEL)),
            new ArrayList<>(Arrays.asList(
                    new Move("Gigaton Hammer", Type.STEEL, false),
                    new Move("Play Rough", Type.FAIRY, false),
                    new Move("Swords Dance", Type.NORMAL, true),
                    new Move("Encore", Type.NORMAL, true)
            ))
    );
    protected Pokemon rotom = new Pokemon(
            "Rotom",
            new ArrayList<>(Arrays.asList(Type.ELECTRIC, Type.WATER)),
            new ArrayList<>(Arrays.asList(
                    new Move("Hydro Pump", Type.WATER, false),
                    new Move("Thunderbolt", Type.ELECTRIC, false),
                    new Move("Hex", Type.GHOST, false),
                    new Move("Nasty Plot", Type.DARK, true)

            ))
    );
    protected Pokemon cetitan = new Pokemon(
            "Cetitan",
            new ArrayList<>(Arrays.asList(Type.ICE)),
            new ArrayList<>(Arrays.asList(
                    new Move("Avalanche", Type.ICE, false),
                    new Move("Ice Shard", Type.ICE, false),
                    new Move("Earthquake", Type.GROUND, false),
                    new Move("Heavy Slam", Type.STEEL, false)

            ))
    );

    protected PokemonList pokemonList;

    @BeforeEach
    public void setup() {
        pokemonList = new PokemonList();
        pokemonList.add(tinkaton);
        pokemonList.add(rotom);
        pokemonList.add(cetitan);
    }

    @Test
    public void testConstructor() {
        assertEquals(Arrays.asList(tinkaton, rotom, cetitan), pokemonList.get());
    }

    @Test
    public void testGet() {
        assertEquals(tinkaton, pokemonList.get(0));
        assertEquals(rotom, pokemonList.get(1));
        assertEquals(cetitan, pokemonList.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> pokemonList.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> pokemonList.get(3));
    }

    @Test
    public void testAdd() {
        assertThrows(IllegalArgumentException.class, () -> pokemonList.add(rotom));
    }

    @Test
    public void testRemove() {
        pokemonList.remove(cetitan);
        assertEquals(Arrays.asList(tinkaton, rotom), pokemonList.get());
        pokemonList.remove(tinkaton);
        assertEquals(Arrays.asList(rotom), pokemonList.get());
    }
}
