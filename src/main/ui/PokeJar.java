package ui;

import model.*;

import java.util.*;

public class PokeJar {

    private Pokemon tinkaton = new Pokemon(
            "Tinkaton",
            Arrays.asList(Type.FAIRY, Type.STEEL),
            Arrays.asList(
                    new Move("Gigaton Hammer", Type.STEEL, false),
                    new Move("Play Rough", Type.FAIRY, false),
                    new Move("Swords Dance", Type.NORMAL, true),
                    new Move("Encore", Type.NORMAL, true)
            )
    );
    private Pokemon rotom = new Pokemon(
            "Rotom",
            Arrays.asList(Type.ELECTRIC, Type.WATER),
            Arrays.asList(
                    new Move("Hydro Pump", Type.WATER, false),
                    new Move("Thunderbolt", Type.ELECTRIC, false),
                    new Move("Hex", Type.GHOST, false),
                    new Move("Nasty Plot", Type.DARK, true)

            )
    );
    private Pokemon cetitan = new Pokemon(
            "Cetitan",
            Arrays.asList(Type.ICE),
            Arrays.asList(
                    new Move("Avalanche", Type.ICE, false),
                    new Move("Ice Shard", Type.ICE, false),
                    new Move("Earthquake", Type.GROUND, false),
                    new Move("Heavy Slam", Type.STEEL, false)

            )
    );

    private Scanner console = new Scanner(System.in);
    private Box box = new Box(new ArrayList<>(Arrays.asList(tinkaton, rotom, cetitan)));
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

    public void startTUI() {
        welcomeMessage();

        while (true) {
            System.out.print("PokéJar > ");
            switch (console.nextLine()) {
                case "b":
                    System.out.println(box);
                    break;
                case "np":
                    box.add(newPokemon());
                    break;
                case "rp":
                    box.remove(getPokemon());
                    break;
                case "ap":
                    System.out.println(getPokemon().analyze());
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
            }
        }
    }

    private static void welcomeMessage() {
        System.out.println();
        System.out.println("Welcome to PokéJar!");
        System.out.println("[b]List Box [np]New Pokémon [rp]Remove Pokémon [ap]Analyze Pokémon");
        System.out.println("[t]List Teams [nt]New Team [rt]Remove Team [at]Analyse Team [q]Quit");
        System.out.println();
    }

    private Pokemon newPokemon() {
        System.out.print("What is the name of your Pokémon? ");
        String name = console.nextLine();
        ArrayList<Type> types;
        while (true) {
            System.out.print("What are the types of your Pokémon? ");
            try {
                types = Type.stringToTypes(console.nextLine());
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
                    type = Type.stringToType(console.nextLine());
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
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                System.out.println("Invalid index, please try again!");
            }
        }
    }

    private Team newTeam() {
        Team team;
        System.out.print("What is the name for this team? ");
        String name = console.nextLine();
        ArrayList<Pokemon> members = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            members.add(getPokemon());
            System.out.print("Would you like to add another Pokémon [y/n]? ");
            if (console.nextLine().equals("n")) {
                break;
            }
        }
        return new Team(name, members);
    }
}
