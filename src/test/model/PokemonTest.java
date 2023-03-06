package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static model.Type.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests model.Pokemon class.
 *
 * @author Anthony Du
 */
public class PokemonTest {
    private final Move MOVE0 = new Move("myMove0", FAIRY, false);
    private final Move MOVE1 = new Move("myMove1", FIRE, true);
    private final Move MOVE2 = new Move("myMove2", GRASS, false);
    private final Move MOVE3 = new Move("myMove3", STEEL, false);

    private Pokemon pokemon;

    @BeforeEach
    public void setup() {
        pokemon = new Pokemon(
                "myPokemon",
                Arrays.asList(DRAGON, FIRE),
                Arrays.asList(MOVE0, MOVE1, MOVE2, MOVE3));
    }

    @Test
    public void testConstructor() {
        assertEquals("myPokemon", pokemon.getName());
        assertEquals(Arrays.asList(DRAGON, FIRE), pokemon.getTypes());
        assertEquals(Arrays.asList(MOVE0, MOVE1, MOVE2, MOVE3), pokemon.getMoves());
        assertThrows(IllegalArgumentException.class, () -> new Pokemon("", Arrays.asList(), Arrays.asList()));
    }

    @Test
    public void testToString() {
        assertEquals(
                "myPokemon       DRAGON          FIRE",
                pokemon.toString()
        );
        pokemon.setName("myAmazingPokemon");
        pokemon.setTypes(Arrays.asList(ROCK));
        assertEquals(
                "myAmazingPokem… ROCK",
                pokemon.toString()
        );
    }

    @Test
    public void testMoveTypes() {
        assertEquals(Arrays.asList(FAIRY, GRASS, STEEL), pokemon.attackingMoveTypes());
        pokemon.setMoves(Arrays.asList(MOVE3, MOVE2, MOVE1, MOVE0));
        assertEquals(Arrays.asList(STEEL, GRASS, FAIRY), pokemon.attackingMoveTypes());
    }
}
