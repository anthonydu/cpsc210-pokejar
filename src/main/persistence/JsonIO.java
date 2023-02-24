package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.PokemonList;
import util.TeamList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonIO {
    private Path filePath;

    /**
     * Constructs a JsonIO object with a path
     *
     * @param filePath a String that is a path to a file
     * @throws InvalidPathException if the path string cannot be converted to a Path
     */
    public JsonIO(String filePath) throws InvalidPathException {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Reads and converts the content of a JsonIO into a JSONObject
     *
     * @return the JSONObject stored in this JsonIO
     * @throws IOException if an I/O error occurs writing to or creating the file
     * @throws JSONException if there is a syntax error in the source string or a duplicated key
     */
    public JSONObject read() throws IOException, JSONException {
        return new JSONObject(new String(Files.readAllBytes(this.filePath)));
    }

    /**
     * Overwrites a JSONObject to a file at this JsonIO's filePath.
     * Creates the file if a file doesn't exist at filePath
     *
     * @param jsonObject the JSONObject to write to this JsonIO
     * @throws IOException if an I/O error occurs reading from the stream
     */
    public void write(JSONObject jsonObject) throws IOException {
        Files.write(this.filePath, jsonObject.toString().getBytes());
    }

    public void saveJarToFile(Jar jar) throws InvalidPathException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("box", jar.getBox().toJson());
        jsonObject.put("teams", jar.getTeams().toJson());
        this.write(jsonObject);
    }

    /**
     *
     * MODIFIES: jar
     *
     * @param jar
     */
    public void loadJarToApp(Jar jar) throws JSONException, IOException {
        jar.setBox(parseBox(this.read().getJSONObject("box")));
        jar.setTeams(parseTeams(this.read().getJSONArray("teams")));
    }

    /**
     * Constructs a TeamList from a JSONArray
     *
     * @return a TeamList of Teams
     * @throws JSONException if teamsArray does not store a valid Team
     */
    public static TeamList parseTeams(JSONArray teamsArray) throws JSONException {
        TeamList teams = new TeamList();
        for (JSONObject team : JsonUtil.objectsFromArray(teamsArray)) {
            teams.add(new Team(
                    team.getString("name"),
                    parsePokemonList(team).get()
            ));
        }
        return teams;
    }

    public static Box parseBox(JSONObject boxObject) throws JSONException {
        return new Box(parsePokemonList(boxObject).get());
    }

    /**
     * Parses a list of Pokemon from pokemonListObject
     *
     * @param pokemonListObject a JSONObject representation of a PokemonList
     * @return a list of Pokemon parsed from pokemonListObject
     * @throws JSONException if pokemonListObject does not store a valid PokemonList
     */
    private static PokemonList parsePokemonList(JSONObject pokemonListObject) throws JSONException {
        PokemonList pokemons = new PokemonList();
        for (JSONObject pokemon : JsonUtil.objectsFromArray(pokemonListObject.getJSONArray("pokemons"))) {
            List<Move> moves = new ArrayList<>();
            for (JSONObject move : JsonUtil.objectsFromArray(pokemon.getJSONArray("moves"))) {
                moves.add(new Move(move.getString("name"),
                        Type.fromString(move.getString("type")),
                        move.getBoolean("status")));
            }
            pokemons.add(new Pokemon(
                    pokemon.getString("name"),
                    Type.fromListOfStrings(JsonUtil.stringsFromArray(pokemon.getJSONArray("types"))),
                    moves
            ));
        }
        return pokemons;
    }
}
