package ui;

import model.Box;
import model.Pokemon;
import model.Type;
import model.Move;

import java.util.Scanner;

public class TUI {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        System.out.println();
        System.out.println("Welcome to PokéJar!");
        System.out.println("[l]List Box [n]New Pokémon [q]Quit");
        System.out.println();

        Box box = new Box();

        while (true) {
            System.out.print("PokéJar > ");
            switch (console.nextLine()) {
                case "l":
                    System.out.println(box.getPokemons().size() == 0 ? "Box empty" : box.toString());
                    break;
                case "n":
                    System.out.print("What is the name of your Pokémon? ");
                    String name = console.nextLine();
                    System.out.print("What are the types of your Pokémon? ");
                    Type[] types = stringToTypes(console.nextLine());
                    Move[] moves = new Move[]{};
                    box.addPokemon(new Pokemon(name, types, moves));
                    break;
                case "q":
                    System.exit(0);
            }
        }
    }

    private static Type[] stringToTypes(String str) {
        String[] strs = str.toLowerCase().split(" ");
        if (strs.length > 2) throw new IllegalArgumentException("More than two types found!");
        Type[] types = new Type[2];
        for (int i = 0; i < strs.length; i++) {
            types[i] = Type.fromString(strs[i]);
        }
        return types;
    }
}
