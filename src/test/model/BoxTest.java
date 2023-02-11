package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.PokemonListTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoxTest extends PokemonListTest {

    @BeforeEach
    public void setup() {
        pokemonList = new Box();
        pokemonList.add(tinkaton);
        pokemonList.add(rotom);
        pokemonList.add(cetitan);
    }

    @Test
    public void testToString() {
        assertEquals(
                "0 Tinkaton        FAIRY           STEEL\n" +
                "1 Rotom           ELECTRIC        WATER\n" +
                "2 Cetitan         ICE",
                pokemonList.toString()
        );
    }
}
