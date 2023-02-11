package ui;

import model.*;
import java.util.*;

/**
 * The PokéJar Application.
 *
 * @author Anthony Du
 */
public class PokeJar {

    private Pokemon tinkaton = new Pokemon(
            "Tinkaton",
            new ArrayList<>(List.of(Type.FAIRY, Type.STEEL)),
            new ArrayList<>(List.of(
                    new Move("Gigaton Hammer", Type.STEEL, false),
                    new Move("Play Rough", Type.FAIRY, false),
                    new Move("Swords Dance", Type.NORMAL, true),
                    new Move("Encore", Type.NORMAL, true)
            ))
    );
    private Pokemon rotom = new Pokemon(
            "Rotom",
            new ArrayList<>(List.of(Type.ELECTRIC, Type.WATER)),
            new ArrayList<>(List.of(
                    new Move("Hydro Pump", Type.WATER, false),
                    new Move("Thunderbolt", Type.ELECTRIC, false),
                    new Move("Hex", Type.GHOST, false),
                    new Move("Nasty Plot", Type.DARK, true)

            ))
    );
    private Pokemon cetitan = new Pokemon(
            "Cetitan",
            new ArrayList<>(List.of(Type.ICE)),
            new ArrayList<>(List.of(
                    new Move("Avalanche", Type.ICE, false),
                    new Move("Ice Shard", Type.ICE, false),
                    new Move("Earthquake", Type.GROUND, false),
                    new Move("Heavy Slam", Type.STEEL, false)

            ))
    );

    private Scanner console = new Scanner(System.in);
    private Box box = new Box(new ArrayList<>(List.of(tinkaton, rotom, cetitan)));
    private List<Team> teams = new ArrayList<>() {
        @Override
        public String toString() {
            String result = "";
            for (int i = 0; i < this.size(); i++) {
                result += i + (i < 10 ? "  " : " ") + this.get(i) + "\n";
            }
            return result.trim();
        }
    };

    public PokeJar() {
        this.startTUI();
    }

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
                    System.out.println(getTeam().analyze());
                    break;
                case "q": System.exit(0);
                default: showCommands();
            }
        }
    }

    private static void welcomeMessage() {
        System.out.println();
        System.out.println("Welcome to PokéJar!");
        showCommands();
        System.out.println();
    }

    private static void showCommands() {
        System.out.println("[l]List Box     [np]New Pokémon [rp]Remove Pokémon [ap]Analyze Pokémon");
        System.out.println("[t]List Teams   [nt]New Team    [rt]Remove Team    [at]Analyse Team    [q]Quit");
    }

    private Pokemon newPokemon() {
        System.out.print("What is the name of your Pokémon? ");
        String name = console.nextLine();
        List<Type> types;
        while (true) {
            System.out.print("What are the types of your Pokémon? ");
            try {
                types = Type.stringToTypes(console.nextLine());
                if (types.size() == 0 || types.size() > 2) {
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

    private Pokemon getPokemon() {
        return this.get("Pokémon", box.get());
    }

    private Team getTeam() {
        return this.get("Team", teams);
    }

    private <T> T get(String thingName, List<T> listOfThings) {
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
     * Returns a String that represents the analysis of this Pokémon
     *
     * @return a String that represents the analysis of this Pokémon
     */
    public static String analyze(Pokemon pokemon) {
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
                + Type.analyzeOffense(moveTypes);
    }
}
