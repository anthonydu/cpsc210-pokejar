package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests model.Pokemon class
 *
 * @author Anthony Du
 */
public class PokemonTest {
    private Move move = new Move("myMove", Type.FAIRY, false);

    private Pokemon pokemon;

    @BeforeEach
    public void setup() {
        pokemon = new Pokemon(
                "myPokemon",
                Arrays.asList(Type.DRAGON, Type.FIRE),
                Arrays.asList(move));
    }

    @Test
    public void testConstructor() {
        assertEquals("myPokemon", pokemon.getName());
        assertEquals(Arrays.asList(Type.DRAGON, Type.FIRE), pokemon.getTypes());
        assertEquals(Arrays.asList(move), pokemon.getMoves());
    }

    @Test
    public void testToString() {
        assertEquals(
                "myPokemon       DRAGON          FIRE",
                pokemon.toString()
        );
        pokemon.setName("myAmazingPokemon");
        pokemon.setTypes(Arrays.asList(Type.ROCK));
        pokemon.setMoves(Arrays.asList(move));
        assertEquals(
                "myAmazingPokem… ROCK",
                pokemon.toString()
        );
    }
}
