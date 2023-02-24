package ui;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import persistence.JsonFile;
import persistence.JsonUtil;
import util.TeamList;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.*;

/**
 * The PokeJar Application.
 *
 * @author Anthony Du
 */
public class PokeJar {
    /**
     * Scanner that scans the console for input
     */
    private Scanner console = new Scanner(System.in);

    /**
     * A box that holds Pokemon, initialized with template items
     */
    private Box box = new Box();

    /**
     * A list of teams with a custom toString method
     */
    private TeamList teams = new TeamList();

    /**
     * Constructs a PokeJar and starts its terminal user interface
     */
    public PokeJar() {
        load(parseJson("autosave"));
        startTUI();
    }

    /**
     * PokeJar's terminal user interface
     *
     * MODIFIES: this
     */
    @SuppressWarnings("methodlength")
    public void startTUI() {
        welcomeMessage();

        while (true) {
            System.out.print("PokéJar > ");
            switch (console.nextLine()) {
                case "p":
                    System.out.println(box);
                    break;
                case "np":
                    box.add(newPokemon());
                    break;
                case "rp":
                    box.remove(getPokemon());
                    break;
                case "ap":
                    try {
                        System.out.println(analyzePokemon(getPokemon()));
                    } catch (IllegalStateException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "t":
                    System.out.println(teams);
                    break;
                case "nt":
                    teams.add(newTeam());
                    break;
                case "rt":
                    teams.remove(getTeam());
                    break;
                case "at":
                    try {
                        System.out.println(analyzeTeam(getTeam()));
                    } catch (IllegalStateException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "l":
                    load();
                    break;
                case "s":
                    save();
                    break;
                case "q":
                    save("./data/autosave.json");
                    System.exit(0);
                default: showCommands();
            }
        }
    }

    /**
     * Prints welcome message
     */
    private static void welcomeMessage() {
        System.out.println();
        System.out.println("Welcome to PokéJar!");
        showCommands();
        System.out.println();
    }

    /**
     * Shows all available commands
     */
    private static void showCommands() {
        System.out.println(""
                + "\u001B[7ml \u001B[0m Load File      \u001B[7ms \u001B[0m Save File      "
                + "\u001B[7mh \u001B[0m Help           \u001B[7mq \u001B[0m Quit");
        System.out.println(""
                + "\u001B[7mp \u001B[0m List Box       \u001B[7mnp\u001B[0m New Pokémon    "
                + "\u001B[7mrp\u001B[0m Remove Pokémon \u001B[7map\u001B[0m Analyze Pokémon");
        System.out.println(""
                + "\u001B[7mt \u001B[0m List Teams     \u001B[7mnt\u001B[0m New Team       "
                + "\u001B[7mrt\u001B[0m Remove Team    \u001B[7mat\u001B[0m Analyse Team");
    }

    /**
     * Asks the user to create a new Pokemon and returns it
     *
     * @return the new Pokemon that the user has created
     */
    private Pokemon newPokemon() {
        System.out.print("What is the name of your Pokémon? ");
        String name = console.nextLine();
        List<Type> types;
        while (true) {
            System.out.print("What are the types of your Pokémon? ");
            try {
                types = Type.fromListOfStrings(Arrays.asList(console.nextLine().split(" ")));
                if (types.isEmpty() || types.size() > 2) {
                    System.out.println("Zero or more than two types found!");
                    continue;
                }
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
        ArrayList<Move> moves = newMoveset();
        return new Pokemon(name, types, moves);
    }

    /**
     * Asks the user to create a new moveset and returns it
     *
     * @return the moveset that the user has created
     */
    private ArrayList<Move> newMoveset() {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            System.out.print("What is the name for Move " + i + "? ");
            String name = console.nextLine();
            Type type;
            while (true) {
                System.out.print("What is the type for Move " + i + "? ");
                try {
                    type = Type.fromString(console.nextLine());
                    break;
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.print("Is this move a status move [y/n]? ");
            boolean isStatus = console.nextLine().equals("y");
            moves.add(new Move(name, type, isStatus));
            System.out.print("Would you like to add another move [y/n]? ");
            if (console.nextLine().equals("n")) {
                break;
            }
        }
        return moves;
    }

    /**
     * Asks the user for an index and returns the Pokemon at that index of the box
     *
     * @return the Pokemon at user specified index
     */
    private Pokemon getPokemon() {
        return this.get("Pokémon", box.get());
    }

    /**
     * Asks the user for an index and returns the Team at that index of the list of teams
     *
     * @return the Pokemon at user specified index
     */
    private Team getTeam() {
        return this.get("Team", teams);
    }

    /**
     * Asks the user for an index and returns the thing at that index of a list of things
     *
     * @param <T> the type that listOfThings holds
     * @param thingName the name of the thing that the user is trying to get
     * @param listOfThings the list of things to get from
     * @return the thing at user specified index
     */
    private <T> T get(String thingName, List<T> listOfThings) throws IllegalStateException {
        if (listOfThings.isEmpty()) {
            throw new IllegalStateException("You have no " + thingName + ".");
        }
        while (true) {
            System.out.print("What is the index of this " + thingName + "? ");
            try {
                int index = Integer.parseInt(console.nextLine());
                return listOfThings.get(index);
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                System.out.println("Invalid index, please try again!");
            }
        }
    }

    /**
     * Asks the user to create a new Team and returns it
     *
     * @return the Team that the user has created
     */
    private Team newTeam() {
        System.out.print("What is the name for this team? ");
        Team team = new Team(console.nextLine());
        for (int i = 1; i <= 6; i++) {
            while (true) {
                try {
                    team.add(getPokemon());
                    break;
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (team.get().size() == this.box.get().size()) {
                System.out.println("You have no more Pokémon in your box to add!");
                break;
            }
            System.out.print("Would you like to add another Pokémon [y/n]? ");
            if (console.nextLine().equals("n")) {
                break;
            }
        }
        return team;
    }

    /**
     * Returns a String that represents the analysis of a Pokemon
     *
     * @param pokemon the Pokemon to analyze
     * @return a String that represents the analysis of a Pokemon
     */
    private static String analyzePokemon(Pokemon pokemon) {
        String movesStr = "";
        for (Move m : pokemon.getMoves()) {
            movesStr += m + "\n";
        }
        return pokemon + "\n"
                + "Multipliers when attacked by moves of type:\n"
                + Type.defensiveMultipliers(pokemon.getTypes()) + "\n"
                + "Moves:\n"
                + movesStr.trim() + "\n"
                + "Multipliers when attacking with your moveset:\n"
                + Type.offensiveMultipliers(pokemon.moveTypes());
    }

    /**
     * Returns a String that represents the analysis of a Team
     *
     * @param team the Team to analyze
     * @return a String that represents the analysis of a Team
     */
    private static String analyzeTeam(Team team) {
        return team + "\n"
                + "Number of Pokemon weak to types:\n"
                + team.numberOfWeakOrResist("weak") + "\n"
                + "Number of Pokemon that resists types:\n"
                + team.numberOfWeakOrResist("resist");
    }

    /**
     * Asks the user for a fileName and saves the current state of PokeJar to ./data/[fileName].json
     */
    private void save() {
        while (true) {
            System.out.print("What is the file name that you want to save as? ");
            String fileName = console.nextLine();
            if (!isValidFileName(fileName)) {
                continue;
            }
            save("./data/" + fileName + ".json");
            break;
        }
    }

    /**
     * Saves the current state of PokeJar to the provided filePath
     *
     * @param filePath the file path to save to
     */
    private void save(String filePath) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("box", box.toJson());
        jsonObject.put("teams", teams.toJson());
        try {
            JsonFile jsonFile = new JsonFile(filePath);
            jsonFile.write(jsonObject);
        } catch (InvalidPathException | IOException ex) {
            System.out.println("Cannot write to file:");
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Asks the user for a fileName, loads JSON, and overwrites the current state of PokeJar
     */
    private void load() {
        JSONObject jsonObject;
        while (true) {
            System.out.print("What is the name of the file that you wish to load [./data/_.json]? ");
            String fileName = console.nextLine();
            if (!isValidFileName(fileName)) {
                continue;
            } else if (parseJson(fileName) != null) {
                jsonObject = parseJson(fileName);
                break;
            }
        }
        System.out.print("This action will overwrite all current data, type \"yes\" to proceed: ");
        if (console.nextLine().equals("yes")) {
            load(jsonObject);
        } else {
            System.out.println("File loading aborted!");
        }
    }

    /**
     * Loads the provided jsonObject and replaces the current state of PokeJar with it
     *
     * MODIFIES: this
     *
     * @param jsonObject the JSONObject to load from
     */
    private void load(JSONObject jsonObject) {
        try {
            this.box = new Box(parsePokemonList(jsonObject.getJSONObject("box")));
            this.teams = parseTeams(jsonObject.getJSONArray("teams"));
        } catch (JSONException ex) {
            System.out.println("JSON is not a valid PokéJar save file:");
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Parses and returns the JSONObject stored in ./data/[fileName].json
     *
     * @param fileName name of the JSON file in ./data/ to load
     * @return the JSONObject stored in ./data/[fileName].json
     */
    private JSONObject parseJson(String fileName) {
        try {
            JsonFile jsonFile = new JsonFile("./data/" + fileName + ".json");
            return jsonFile.read();
        } catch (InvalidPathException | IOException | JSONException ex) {
            System.out.println("Cannot parse JSON from file:");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Constructs a TeamList from a JSONArray
     *
     * @param teamsArray an JSONArray representation of a Team
     * @return a TeamList of Teams
     * @throws JSONException if teamsArray does not store a valid Team
     */
    private TeamList parseTeams(JSONArray teamsArray) throws JSONException {
        TeamList teams = new TeamList();
        for (JSONObject team : JsonUtil.objectsFromArray(teamsArray)) {
            teams.add(new Team(
                    team.getString("name"),
                    parsePokemonList(team)
            ));
        }
        return teams;
    }

    /**
     * Parses a list of Pokemon from pokemonListObject
     *
     * @param pokemonListObject a JSONObject representation of a PokemonList
     * @return a list of Pokemon parsed from pokemonListObject
     * @throws JSONException if pokemonListObject does not store a valid PokemonList
     */
    private List<Pokemon> parsePokemonList(JSONObject pokemonListObject) throws JSONException {
        List<Pokemon> pokemons = new ArrayList<>();
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

    /**
     * Checks if a fileName is valid (does not contain ".")
     *
     * @param fileName a file name string
     * @return true if fileName is valid, false otherwise
     */
    private static boolean isValidFileName(String fileName) {
        if (fileName.contains(".")) {
            System.out.println("File name cannot contain \".\"!");
            return false;
        }
        return true;
    }

    // TODO isValidFile: check if all pokemon in teams exists in the box
}
