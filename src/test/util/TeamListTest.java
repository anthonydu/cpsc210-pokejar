package util;

import model.Team;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamListTest extends TestSubjects {
    private TeamList teams;

    private static final String jsonString = "" +
            "[\n" +
            "  {\n" +
            "    \"pokemons\": [\n" +
            "      {\n" +
            "        \"types\": [\n" +
            "          \"FAIRY\",\n" +
            "          \"STEEL\"\n" +
            "        ],\n" +
            "        \"moves\": [\n" +
            "          {\n" +
            "            \"name\": \"Gigaton Hammer\",\n" +
            "            \"type\": \"STEEL\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Play Rough\",\n" +
            "            \"type\": \"FAIRY\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Swords Dance\",\n" +
            "            \"type\": \"NORMAL\",\n" +
            "            \"status\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Encore\",\n" +
            "            \"type\": \"NORMAL\",\n" +
            "            \"status\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"name\": \"Tinkaton\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"types\": [\n" +
            "          \"ELECTRIC\",\n" +
            "          \"WATER\"\n" +
            "        ],\n" +
            "        \"moves\": [\n" +
            "          {\n" +
            "            \"name\": \"Hydro Pump\",\n" +
            "            \"type\": \"WATER\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Thunderbolt\",\n" +
            "            \"type\": \"ELECTRIC\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Hex\",\n" +
            "            \"type\": \"GHOST\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Nasty Plot\",\n" +
            "            \"type\": \"DARK\",\n" +
            "            \"status\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"name\": \"Rotom\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"types\": [\n" +
            "          \"ICE\"\n" +
            "        ],\n" +
            "        \"moves\": [\n" +
            "          {\n" +
            "            \"name\": \"Avalanche\",\n" +
            "            \"type\": \"ICE\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Ice Shard\",\n" +
            "            \"type\": \"ICE\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Earthquake\",\n" +
            "            \"type\": \"GROUND\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Heavy Slam\",\n" +
            "            \"type\": \"STEEL\",\n" +
            "            \"status\": false\n" +
            "          }\n" +
            "        ],\n" +
            "        \"name\": \"Cetitan\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"name\": \"myTeam\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"pokemons\": [\n" +
            "      {\n" +
            "        \"types\": [\n" +
            "          \"ELECTRIC\",\n" +
            "          \"WATER\"\n" +
            "        ],\n" +
            "        \"moves\": [\n" +
            "          {\n" +
            "            \"name\": \"Hydro Pump\",\n" +
            "            \"type\": \"WATER\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Thunderbolt\",\n" +
            "            \"type\": \"ELECTRIC\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Hex\",\n" +
            "            \"type\": \"GHOST\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Nasty Plot\",\n" +
            "            \"type\": \"DARK\",\n" +
            "            \"status\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"name\": \"Rotom\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"types\": [\n" +
            "          \"FAIRY\",\n" +
            "          \"STEEL\"\n" +
            "        ],\n" +
            "        \"moves\": [\n" +
            "          {\n" +
            "            \"name\": \"Gigaton Hammer\",\n" +
            "            \"type\": \"STEEL\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Play Rough\",\n" +
            "            \"type\": \"FAIRY\",\n" +
            "            \"status\": false\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Swords Dance\",\n" +
            "            \"type\": \"NORMAL\",\n" +
            "            \"status\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"Encore\",\n" +
            "            \"type\": \"NORMAL\",\n" +
            "            \"status\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"name\": \"Tinkaton\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"name\": \"myTeam\"\n" +
            "  }\n" +
            "]";

    @BeforeEach
    public void setup() {
        teams = new TeamList();
        teams.add(new Team("myTeam", Arrays.asList(tinkaton, rotom, cetitan)));
        teams.add(new Team("myTeam", Arrays.asList(rotom, tinkaton)));
    }

    @Test
    public void testToString() {
        assertEquals(
                "0 myTeam          Tinkaton        Rotom           Cetitan\n" +
                "1 myTeam          Rotom           Tinkaton",
                teams.toString());
    }

    @Test
    public void testToJson() {
        assertEquals(new JSONArray(jsonString).toString(), teams.toJson().toString());
    }
}
