package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.PokemonListTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test @Override
    public void testToJson() {
        assertEquals(
                new JSONObject("{\"name\":\"myTeam\"," + PokemonsJsonString + "}").toString(),
                team.toJson().toString()
        );
    }

    @Test
    public void testNumberOfWeakOrResist() {
        assertEquals(
                "{NORMAL=0, FIRE=2, WATER=0, GRASS=1, ELECTRIC=0, ICE=0, FIGHTING=1, POISON=0, GROUND=2, " +
                "FLYING=0, PSYCHIC=0, BUG=0, ROCK=1, GHOST=0, DRAGON=0, DARK=0, STEEL=1, FAIRY=0}",
                team.numberOfWeakOrResist("weak").toString());
        assertEquals(
                "{NORMAL=1, FIRE=1, WATER=1, GRASS=1, ELECTRIC=0, ICE=3, FIGHTING=0, POISON=1, GROUND=0, " +
                "FLYING=2, PSYCHIC=1, BUG=1, ROCK=1, GHOST=0, DRAGON=1, DARK=1, STEEL=1, FAIRY=1}",
                team.numberOfWeakOrResist("resist").toString());
        assertThrows(IllegalArgumentException.class, () -> team.numberOfWeakOrResist("immune"));
    }
}
