package ui;

import model.*;
import org.json.JSONException;
import persistence.InvalidJarException;
import persistence.JsonFile;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The PokeJar Application.
 *
 * @author Anthony Du
 */
public class PokeJar {
    private Scanner console;
    private Jar jar;

    /**
     * Constructs a new PokeJar app and starts its terminal user interface with autosave loaded.
     */
    public PokeJar() {
        console = new Scanner(System.in);
        jar = new Jar();
        load("autosave");
        startTUI();
    }

    /**
     * Starts PokeJar's terminal user interface.
     * <p>
     * MODIFIES: this
     */
    @SuppressWarnings("methodlength")
    private void startTUI() {
        welcomeMessage();

        while (true) {
            System.out.print("PokéJar > ");
            try {
                switch (console.nextLine()) {
                    case "p":
                        System.out.println(jar.getBox());
                        break;
                    case "np":
                        if (!jar.getBox().add(newPokemon())) {
                            System.out.println("Cannot add multiple Pokémon with the same name!");
                        }
                        break;
                    case "rp":
                        jar.getBox().remove(getPokemon());
                        break;
                    case "ap":
                        System.out.println(analyzePokemon(getPokemon()));
                        break;
                    case "t":
                        System.out.println(jar.getTeams());
                        break;
                    case "nt":
                        jar.getTeams().add(newTeam());
                        break;
                    case "rt":
                        jar.getTeams().remove(getTeam());
                        break;
                    case "at":
                        System.out.println(analyzeTeam(getTeam()));
                        break;
                    case "l":
                        loadDialog();
                        break;
                    case "s":
                        saveDialog();
                        break;
                    case "q":
                        save("autosave");
                        System.exit(0);
                    default: showCommands();
                }
            } catch (IllegalStateException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Prints welcome message.
     */
    private static void welcomeMessage() {
        System.out.println();
        System.out.println("Welcome to PokéJar!");
        showCommands();
        System.out.println();
    }

    /**
     * Shows all available commands.
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
     * Asks the user to create a new Pokemon and returns it.
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
            } catch (PokemonTypeException ex) {
                System.out.println(ex.getMessage());
            }
        }
        ArrayList<Move> moves = newMoveset();
        return new Pokemon(name, types, moves);
    }

    /**
     * Asks the user to create a new moveset and returns it.
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
                } catch (PokemonTypeException ex) {
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
     * Asks the user for an index and returns the Pokemon at that index of the box.
     *
     * @return the Pokemon at user specified index
     * @throws IllegalStateException if box is empty
     */
    private Pokemon getPokemon() throws IllegalStateException {
        return this.get("Pokémon", jar.getBox());
    }

    /**
     * Asks the user for an index and returns the Team at that index of the list of teams.
     *
     * @return the Pokemon at user specified index
     * @throws IllegalStateException if teams is empty
     */
    private Team getTeam() throws IllegalStateException {
        return this.get("Team", jar.getTeams());
    }

    /**
     * Asks the user for an index and returns the thing at that index of a list of things.
     *
     * @param <T> the type that listOfThings holds
     * @param thingName the name of the thing that the user is trying to get
     * @param listOfThings the list of things to get from
     * @return the thing at user specified index
     * @throws IllegalStateException if the listOfThings is empty
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
     * Asks the user to create a new Team and returns it.
     *
     * @return the Team that the user has created
     */
    private Team newTeam() {
        System.out.print("What is the name for this team? ");
        Team team = new Team(console.nextLine());
        for (int i = 1; i <= 6; i++) {
            while (true) {
                try {
                    team.getPokemons().add(getPokemon());
                    break;
                } catch (IllegalStateException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (team.getPokemons().size() == jar.getBox().size()) {
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
     * Generates a String that represents the analysis of a Pokemon.
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
                + Type.offensiveMultipliers(pokemon.attackingMoveTypes());
    }

    /**
     * Generates a String that represents the analysis of a Team.
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
     * Asks the user for a fileName and saves the current state of PokeJar to file.
     */
    private void saveDialog() {
        String fileName;
        while (true) {
            System.out.print("What is the file name that you want to save as? ");
            fileName = console.nextLine();
            if (fileName.contains(".")) {
                System.out.println("File name cannot contain \".\"!");
            } else if (fileName.equals("testSaveJarToFile")) {
                System.out.println("File name cannot be \"testSaveJarToFile\"!");
            } else {
                break;
            }
        }
        save(fileName);
    }

    /**
     * Saves the current state of PokeJar to ./data/[fileName].json.
     *
     * @param fileName the name of the destination file
     */
    private void save(String fileName) {
        try {
            new JsonFile("./data/" + fileName + ".json").saveJarToFile(jar);
            System.out.println("Current app data successfully saved to ./data/" + fileName + ".json!");
        } catch (InvalidPathException | IOException ex) {
            System.out.println("Cannot write to file:" + ex.getMessage());
        }
    }


    /**
     * Asks the user for a fileName, loads JSON, and overwrites the current state of PokeJar.
     * <p>
     * MODIFIES: this
     */
    private void loadDialog() {
        String fileName;
        while (true) {
            System.out.print("What is the name of the file that you wish to load [./data/_.json]? ");
            fileName = console.nextLine();
            if (!fileName.contains(".")) {
                break;
            }
            System.out.println("File name cannot contain \".\"!");
        }
        System.out.print("This action will overwrite all current data, type \"yes\" to proceed: ");
        if (!console.nextLine().equals("yes")) {
            System.out.println("File loading aborted!");
        }
        load(fileName);
    }

    /**
     * Loads JSON at ./data/[fileName].json, and overwrites the current state of PokeJar.
     * <p>
     * MODIFIES: this
     *
     * @param fileName the source file name
     */
    private void load(String fileName) {
        try {
            new JsonFile("./data/" + fileName + ".json").loadFileToJar(jar);
            System.out.println("./data/" + fileName + ".json has been loaded to PokéJar!");
        } catch (IOException | JSONException | InvalidJarException ex) {
            System.out.println("Cannot load JSON: " + ex.getMessage());
        }
    }
}
