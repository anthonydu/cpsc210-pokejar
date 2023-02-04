package model;

import java.util.Arrays;

public class Pokemon {
    private String name;
    private Type[] types;
    private Move[] moves;

    // REQUIRES: name.length() <= 16
    //           types.length <= 2 with no duplicates
    //           moves.length <= 4 with no duplicates
    // EFFECTS: Constructs a Pokémon with name, types, and moves
    public Pokemon(String name, Type[] types, Move[] moves) {
        this.name = name;
        this.types = types;
        this.moves = moves;
    }

    public String getName() {
        return this.name;
    }

    public Type[] getTypes() {
        return this.types;
    }

    public Move[] getMoves() {
        return this.moves;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTypes(Type[] types) {
        this.types = types;
    }

    public void setMoves(Move[] moves) {
        this.moves = moves;
    }

    @Override
    public String toString() {
        return this.name + " ".repeat(16 - this.name.length()) +
                this.types[0].name() + " ".repeat(9 - this.types[0].name().length()) +
                this.types[1].name() + " ".repeat(9 - this.types[1].name().length());
    }
}
