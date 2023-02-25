package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import model.TeamList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * An object that handles the saving and loading of Jars from a JsonFile.
 *
 * @author Anthony Du
 */
public class JsonFile {
    private Path filePath;

    /**
     * Constructs a JsonFile object with a path
     *
     * @param filePath a String that is a path to a file
     * @throws InvalidPathException if the path string cannot be converted to a Path
     */
    public JsonFile(String filePath) throws InvalidPathException {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Gets the Path to this JsonFile.
     *
     * @return the Path to this JsonFile
     */
    public Path getFilePath() {
        return filePath;
    }

    /**
     * Reads and converts the content of this JsonFile to a JSONObject.
     *
     * @return the JSONObject stored in this JsonFile
     * @throws IOException if an I/O error occurs writing to or creating the file
     * @throws JSONException if there is a syntax error in the source string or a duplicated key
     */
    public JSONObject read() throws IOException, JSONException {
        return new JSONObject(new String(Files.readAllBytes(this.filePath)));
    }

    /**
     * Writes a JSONObject to a file at this JsonFile's filePath.
     * Creates the file if a file doesn't exist at filePath.
     * Overwrites the file if a file already exists at filePath.
     *
     * @param jsonObject the JSONObject to write to this JsonFile
     * @throws IOException if an I/O error occurs reading from the stream
     */
    public void write(JSONObject jsonObject) throws IOException {
        Files.write(this.filePath, jsonObject.toString().getBytes());
    }

    /**
     * Saves the given Jar to this JsonFile's path.
     * If a JSON file already exists at filePath, it will be overwritten.
     *
     * @param jar the Jar to convert and save as JSON
     * @throws IOException if this JsonFile cannot write to its path
     */
    public void saveJarToFile(Jar jar) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("box", jar.getBox());
        jsonObject.put("teams", jar.getTeams());
        this.write(jsonObject);
    }

    /**
     * Parses and loads the content of a JsonFile into the given Jar.
     * All contents in the given Jar will be overwritten.
     * <p>
     * MODIFIES: jar
     *
     * @param jar the Jar to load the content of the file to
     * @throws JSONException if the JSON at filePath is not a valid Jar save
     * @throws IOException if an I/O error occurs writing to or creating the file
     */
    public void loadFileToJar(Jar jar) throws JSONException, IOException {
        jar.setBox(parseBox(this.read().getJSONArray("box")));
        jar.setTeams(parseTeams(this.read().getJSONArray("teams")));
    }

    /**
     * Constructs a TeamList from a JSONArray.
     *
     * @param teamsArray a JSONArray that contains information of a TeamList
     * @return a TeamList of Teams
     * @throws JSONException if teamsArray does not store a valid Team
     */
    private static TeamList parseTeams(JSONArray teamsArray) throws JSONException {
        TeamList teams = new TeamList();
        for (JSONObject team : JsonUtil.objectsFromArray(teamsArray)) {
            teams.add(new Team(
                    team.getString("name"),
                    parseListOfPokemons(team.getJSONArray("pokemons"))
            ));
        }
        return teams;
    }

    /**
     * Parses and constructs a Box from a JSONArray.
     *
     * @param boxArray a JSONArray that contains information of a Box
     * @return a Box parsed from a JSONArray
     * @throws JSONException if listArray cannot be converted to a Box
     */
    private static Box parseBox(JSONArray boxArray) throws JSONException {
        return new Box(parseListOfPokemons(boxArray));
    }

    /**
     * Parses and constructs a list of Pokemon from a JSONArray.
     *
     * @param jsonArray a JSONObject representation of a PokemonList
     * @return a list of Pokemon parsed from listArray
     * @throws JSONException if listArray cannot be converted to a PokemonList
     */
    private static List<Pokemon> parseListOfPokemons(JSONArray jsonArray) throws JSONException {
        List<Pokemon> pokemons = new ArrayList<>();
        for (JSONObject pokemon : JsonUtil.objectsFromArray(jsonArray)) {
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