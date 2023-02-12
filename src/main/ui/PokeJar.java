package ui;

import model.*;
import java.util.*;

/**
 * The PokeJar Application.
 *
 * @author Anthony Du
 */
public class PokeJar {

    /**
     * A Pokemon used as template item for box
     */
    private Pokemon tinkaton = new Pokemon(
            "Tinkaton",
            new ArrayList<>(Arrays.asList(Type.FAIRY, Type.STEEL)),
            new ArrayList<>(Arrays.asList(
                    new Move("Gigaton Hammer", Type.STEEL, false),
                    new Move("Play Rough", Type.FAIRY, false),
                    new Move("Swords Dance", Type.NORMAL, true),
                    new Move("Encore", Type.NORMAL, true)
            ))
    );

    /**
     * A Pokemon used as template item for box
     */
    private Pokemon rotom = new Pokemon(
            "Rotom",
            new ArrayList<>(Arrays.asList(Type.ELECTRIC, Type.WATER)),
            new ArrayList<>(Arrays.asList(
                    new Move("Hydro Pump", Type.WATER, false),
                    new Move("Thunderbolt", Type.ELECTRIC, false),
                    new Move("Hex", Type.GHOST, false),
                    new Move("Nasty Plot", Type.DARK, true)

            ))
    );

    /**
     * A Pokemon used as template item for box
     */
    private Pokemon cetitan = new Pokemon(
            "Cetitan",
            new ArrayList<>(Arrays.asList(Type.ICE)),
            new ArrayList<>(Arrays.asList(
                    new Move("Avalanche", Type.ICE, false),
                    new Move("Ice Shard", Type.ICE, false),
                    new Move("Earthquake", Type.GROUND, false),
                    new Move("Heavy Slam", Type.STEEL, false)

            ))
    );

    /**
     * Scanner that scans the console for input
     */
    private Scanner console = new Scanner(System.in);

    /**
     * A box that holds Pokemon, initialized with template items
     */
    private Box box = new Box(new ArrayList<>(Arrays.asList(tinkaton, rotom, cetitan)));

    /**
     * A list of teams with a custom toString method
     */
    private List<Team> teams = new ArrayList<Team>() {
        @Override
        public String toString() {
            String result = "";
            for (int i = 0; i < this.size(); i++) {
                result += i + (i < 10 ? "  " : " ") + this.get(i) + "\n";
            }
            return result.trim();
        }
    };

    /**
     * Constructs a PokeJar and starts its terminal user interface
     */
    public PokeJar() {
        this.startTUI();
    }

    /**
     * PokeJar's terminal user interface
     */
    @SuppressWarnings("methodlength")
    public void startTUI() {
        welcomeMessage();

        while (true) {
            System.out.print("PokéJar > ");
            switch (console.nextLine()) {
                case "l":
                    System.out.println(box);
                    break;
                case "np":
                    box.add(newPokemon());
                    break;
                case "rp":
                    box.remove(getPokemon());
                    break;
                case "ap":
                    System.out.println(analyze(getPokemon()));
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
                    System.out.println("Not yet implemented");
                    // TODO System.out.println(getTeam().analyze());
                    break;
                case "q": System.exit(0);
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
        System.out.println("[l]List Box   [np]New Pokémon [rp]Remove Pokémon [ap]Analyze Pokémon [h]Help");
        System.out.println("[t]List Teams [nt]New Team    [rt]Remove Team    [at]Analyse Team    [q]Quit");
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
                types = Type.fromSpaceSeparatedString(console.nextLine());
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
    private <T> T get(String thingName, List<T> listOfThings) {
        if (listOfThings.isEmpty()) {
            System.out.println("You have no " + thingName + " to remove.");
            return null;
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
    private static String analyze(Pokemon pokemon) {
        String movesStr = "";
        List<Type> moveTypes = new ArrayList<>();
        for (Move m : pokemon.getMoves()) {
            movesStr += m + "\n";
            moveTypes.add(m.getType());
        }
        return pokemon + "\n"
                + "Multipliers when attacked by moves of type:\n"
                + Type.defensiveMultipliers(pokemon.getTypes()) + "\n"
                + "Moves:\n"
                + movesStr.trim() + "\n"
                + "Multipliers when attacking with your moveset:\n"
                + Type.offensiveMultipliers(moveTypes);
    }
}
