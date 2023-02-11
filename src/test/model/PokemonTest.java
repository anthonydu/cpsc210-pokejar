package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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
                List.of(Type.DRAGON, Type.FIRE),
                List.of(move));
    }

    @Test
    public void testConstructor() {
        assertEquals("myPokemon", pokemon.getName());
        assertEquals(List.of(Type.DRAGON, Type.FIRE), pokemon.getTypes());
        assertEquals(List.of(move), pokemon.getMoves());
    }

    @Test
    public void testToString() {
        assertEquals(
                "myPokemon       DRAGON          FIRE",
                pokemon.toString()
        );
        pokemon.setName("myAmazingPokemon");
        pokemon.setTypes(List.of(Type.ROCK));
        pokemon.setMoves(List.of(move));
        assertEquals(
                "myAmazingPokem… ROCK",
                pokemon.toString()
        );
    }
}
