package util;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests model.Box class.
 *
 * @author Anthony Du
 */
public class PokemonListTest extends TestSubjects {
    protected PokemonList pokemonList;

    protected static final String PokemonsJsonString = "" +
            "  \"pokemons\": [\n" +
            "    {\n" +
            "      \"types\": [\n" +
            "        \"FAIRY\",\n" +
            "        \"STEEL\"\n" +
            "      ],\n" +
            "      \"moves\": [\n" +
            "        {\n" +
            "          \"name\": \"Gigaton Hammer\",\n" +
            "          \"type\": \"STEEL\",\n" +
            "          \"status\": false\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Play Rough\",\n" +
            "          \"type\": \"FAIRY\",\n" +
            "          \"status\": false\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Swords Dance\",\n" +
            "          \"type\": \"NORMAL\",\n" +
            "          \"status\": true\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Encore\",\n" +
            "          \"type\": \"NORMAL\",\n" +
            "          \"status\": true\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"Tinkaton\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"types\": [\n" +
            "        \"ELECTRIC\",\n" +
            "        \"WATER\"\n" +
            "      ],\n" +
            "      \"moves\": [\n" +
            "        {\n" +
            "          \"name\": \"Hydro Pump\",\n" +
            "          \"type\": \"WATER\",\n" +
            "          \"status\": false\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Thunderbolt\",\n" +
            "          \"type\": \"ELECTRIC\",\n" +
            "          \"status\": false\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hex\",\n" +
            "          \"type\": \"GHOST\",\n" +
            "          \"status\": false\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Nasty Plot\",\n" +
            "          \"type\": \"DARK\",\n" +
            "          \"status\": true\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"Rotom\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"types\": [\n" +
            "        \"ICE\"\n" +
            "      ],\n" +
            "      \"moves\": [\n" +
            "        {\n" +
            "          \"name\": \"Avalanche\",\n" +
            "          \"type\": \"ICE\",\n" +
            "          \"status\": false\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Ice Shard\",\n" +
            "          \"type\": \"ICE\",\n" +
            "          \"status\": false\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Earthquake\",\n" +
            "          \"type\": \"GROUND\",\n" +
            "          \"status\": false\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Heavy Slam\",\n" +
            "          \"type\": \"STEEL\",\n" +
            "          \"status\": false\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"Cetitan\"\n" +
            "    }\n" +
            "  ]\n";

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

    @Test
    public void testToJson() {
        assertEquals(
                new JSONObject("{" + PokemonsJsonString + "}").toString(),
                pokemonList.toJson().toString()
        );
    }
}
