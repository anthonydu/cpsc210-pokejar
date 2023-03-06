package persistence;

import model.Box;
import model.Jar;
import model.Team;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestSubjects;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the persistence package.
 *
 * @author Anthony Du
 */
public class JsonTest extends TestSubjects {
    JsonFile testFile = new JsonFile("./data/testSaveJarToFile.json");
    Jar jar;

    private static final String expectedJsonString = "" +
            "{\n" +
            "  \"teams\": [\n" +
            "    {\n" +
            "      \"pokemons\": [\n" +
            "        {\n" +
            "          \"types\": [\n" +
            "            \"FAIRY\",\n" +
            "            \"STEEL\"\n" +
            "          ],\n" +
            "          \"moves\": [\n" +
            "            {\n" +
            "              \"name\": \"Gigaton Hammer\",\n" +
            "              \"type\": \"STEEL\",\n" +
            "              \"status\": false\n" +
            "            },\n" +
            "            {\n" +
            "              \"name\": \"Play Rough\",\n" +
            "              \"type\": \"FAIRY\",\n" +
            "              \"status\": false\n" +
            "            },\n" +
            "            {\n" +
            "              \"name\": \"Swords Dance\",\n" +
            "              \"type\": \"NORMAL\",\n" +
            "              \"status\": true\n" +
            "            },\n" +
            "            {\n" +
            "              \"name\": \"Encore\",\n" +
            "              \"type\": \"NORMAL\",\n" +
            "              \"status\": true\n" +
            "            }\n" +
            "          ],\n" +
            "          \"name\": \"Tinkaton\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"types\": [\n" +
            "            \"ELECTRIC\",\n" +
            "            \"WATER\"\n" +
            "          ],\n" +
            "          \"moves\": [\n" +
            "            {\n" +
            "              \"name\": \"Hydro Pump\",\n" +
            "              \"type\": \"WATER\",\n" +
            "              \"status\": false\n" +
            "            },\n" +
            "            {\n" +
            "              \"name\": \"Thunderbolt\",\n" +
            "              \"type\": \"ELECTRIC\",\n" +
            "              \"status\": false\n" +
            "            },\n" +
            "            {\n" +
            "              \"name\": \"Hex\",\n" +
            "              \"type\": \"GHOST\",\n" +
            "              \"status\": false\n" +
            "            },\n" +
            "            {\n" +
            "              \"name\": \"Nasty Plot\",\n" +
            "              \"type\": \"DARK\",\n" +
            "              \"status\": true\n" +
            "            }\n" +
            "          ],\n" +
            "          \"name\": \"Rotom\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"types\": [\n" +
            "            \"ICE\"\n" +
            "          ],\n" +
            "          \"moves\": [\n" +
            "            {\n" +
            "              \"name\": \"Avalanche\",\n" +
            "              \"type\": \"ICE\",\n" +
            "              \"status\": false\n" +
            "            },\n" +
            "            {\n" +
            "              \"name\": \"Ice Shard\",\n" +
            "              \"type\": \"ICE\",\n" +
            "              \"status\": false\n" +
            "            },\n" +
            "            {\n" +
            "              \"name\": \"Earthquake\",\n" +
            "              \"type\": \"GROUND\",\n" +
            "              \"status\": false\n" +
            "            },\n" +
            "            {\n" +
            "              \"name\": \"Heavy Slam\",\n" +
            "              \"type\": \"STEEL\",\n" +
            "              \"status\": false\n" +
            "            }\n" +
            "          ],\n" +
            "          \"name\": \"Cetitan\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"name\": \"myTeam\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"box\": [\n" +
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
            "  ]\n" +
            "}";

    String invalidJsonString = "" +
            "{\n" +
            "  \"teams\": [\n" +
            "    {\n" +
            "    \"pokemons\": [\n" +
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
            "    }\n" +
            "  ],\n" +
            "    \"name\": \"myTeam\"\n" +
            "    }],\n" +
            "  \"box\": []\n" +
            "}";

    String illegalJsonString = "" +
            "{\n" +
            "  \"teams\": [\n" +
            "    {\n" +
            "    \"pokemons\": [\n" +
            "    {\n" +
            "      \"types\": [\n" +
            "        \"FAIRY\",\n" +
            "        \"SOUND\"\n" +
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
            "    }\n" +
            "  ],\n" +
            "    \"name\": \"myTeam\"\n" +
            "    }],\n" +
            "  \"box\": []\n" +
            "}";

    @BeforeEach
    public void setup() throws Exception {
        jar = new Jar();
        // gives jar properties and saves it to test file
        jar.setBox(new Box(Arrays.asList(tinkaton, rotom, cetitan)));
        jar.getTeams().add(new Team("myTeam", Arrays.asList(tinkaton, rotom, cetitan)));
        testFile.saveJarToFile(jar);
        // gives jar different properties and loads the saved test file
        jar.setBox(new Box(Arrays.asList(rotom, tinkaton, cetitan)));
        jar.getTeams().add(new Team("myTeam", Arrays.asList(cetitan, tinkaton, rotom)));
        testFile.loadFileToJar(jar);
    }

    @Test
    public void testSaveJarToFile() throws IOException {
        assertEquals(new JSONObject(expectedJsonString).toString(), testFile.read().toString());
    }

    @Test
    public void testLoadJarToApp() throws IOException {
        assertEquals(new JSONObject(expectedJsonString).toString(), new JSONObject(jar).toString());
        assertThrows(IOException.class, () -> new JsonFile("./data/invalid/.json").loadFileToJar(jar));
        assertThrows(JSONException.class, () -> new JsonFile("./data/invalid/syntax.json").loadFileToJar(jar));
        assertThrows(InvalidJarException.class, () -> new JsonFile("./data/invalid/team.json").loadFileToJar(jar));
        assertThrows(InvalidJarException.class, () -> new JsonFile("./data/invalid/type.json").loadFileToJar(jar));
        assertThrows(InvalidJarException.class, () -> new JsonFile("./data/invalid/duplicate.json").loadFileToJar(jar));
    }

    @AfterEach
    public void reset() throws IOException {
        Files.delete(testFile.getFilePath());
    }
}
