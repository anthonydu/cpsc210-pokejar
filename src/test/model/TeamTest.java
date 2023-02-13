package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.PokemonListTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests model.Team class
 *
 * @author Anthony Du
 */
public class TeamTest extends PokemonListTest {
    private Team team;

    @BeforeEach
    public void setup() {
        pokemonList = new Team("myTeam");
        pokemonList.add(tinkaton);
        pokemonList.add(rotom);
        pokemonList.add(cetitan);
        team = (Team) pokemonList;
    }

    @Test
    public void testConstructor() {
        assertEquals("myTeam", team.getName());
    }

    @Test
    public void testToString() {
        assertEquals(
                "myTeam          Tinkaton        Rotom           Cetitan",
                team.toString()
        );
        team.setName("myAmazingLilTeam");
        assertEquals(
                "myAmazingLilTe… Tinkaton        Rotom           Cetitan",
                team.toString()
        );
    }
}
