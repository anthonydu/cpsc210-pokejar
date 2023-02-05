package ui;

import model.Box;
import model.Pokemon;
import model.Type;
import model.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TUI {
    private static void welcomeMessage() {
        System.out.println();
        System.out.println("Welcome to PokéJar!");
        System.out.println("[l]List Box [n]New Pokémon [a]Analyze Pokémon [q]Quit");
        System.out.println();
    }

    private static Pokemon newPokemon(Scanner scanner) {
        System.out.print("What is the name of your Pokémon? ");
        String name = scanner.nextLine();
        ArrayList<Type> types;
        while (true) {
            System.out.print("What are the types of your Pokémon? ");
            try {
                types = Type.stringToTypes(scanner.nextLine());
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
        ArrayList<Move> moves = newMoveset(scanner);
        return new Pokemon(name, types, moves);
    }

    private static ArrayList<Move> newMoveset(Scanner scanner) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            System.out.print("What is the name for Move " + i + "? ");
            String name = scanner.nextLine();
            Type type;
            while (true) {
                System.out.print("What is the type for Move " + i + "? ");
                try {
                    type = Type.stringToType(scanner.nextLine());
                    break;
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.print("Is this move a status move [y/n]? ");
            boolean isStatus = scanner.nextLine().equals("y");
            moves.add(new Move(name, type, isStatus));
            System.out.print("Would you like to add another move [y/n]? ");
            if (scanner.nextLine().equals("n")) {
                break;
            }
        }
        return moves;
    }

    private static Pokemon getPokemon(Scanner scanner, Box box) {
        while (true) {
            System.out.print("What is the index of this Pokémon? ");
            try {
                int index = Integer.parseInt(scanner.nextLine());
                return box.getPokemon(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                System.out.println("Invalid index, please try again!");
            }
        }
    }

    static Pokemon tinkaton = new Pokemon(
            "Tinkaton",
            Arrays.asList(Type.FAIRY, Type.STEEL),
            Arrays.asList(
                    new Move("Gigaton Hammer", Type.STEEL, false),
                    new Move("Play Rough", Type.FAIRY, false),
                    new Move("Swords Dance", Type.NORMAL, true),
                    new Move("Encore", Type.NORMAL, true)
            )
    );

    static Pokemon rotom = new Pokemon(
            "Rotom",
            Arrays.asList(Type.ELECTRIC, Type.WATER),
            Arrays.asList(
                    new Move("Hydro Pump", Type.WATER, false),
                    new Move("Thunderbolt", Type.ELECTRIC, false),
                    new Move("Hex", Type.GHOST, false),
                    new Move("Nasty Plot", Type.DARK, true)

            )
    );

    public static void main(String[] args) {
        welcomeMessage();

        Scanner console = new Scanner(System.in);

        Box box = new Box(new ArrayList<>(Arrays.asList(tinkaton, rotom)));

        while (true) {
            System.out.print("PokéJar > ");
            switch (console.nextLine()) {
                case "l":
                    System.out.println(box);
                    break;
                case "n":
                    box.addPokemon(newPokemon(console));
                    break;
                case "a":
                    System.out.println(getPokemon(console, box).analyzePokemon());
                    break;
                case "q": System.exit(0);
            }
        }
    }
}
