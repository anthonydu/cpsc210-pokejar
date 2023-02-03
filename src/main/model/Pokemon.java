package model;

public class Pokemon {
    private String name;
    private Type[] types;
    private Move[] moves;

    // REQUIRES: types.length <= 2 with no duplicates
    //           moves.length <= 4 with no duplicates
    // EFFECTS: Constructs a Pokémon with name, types, and moves
    public Pokemon(String name, Type[] types, Move[] moves) {
        this.name = name;
        this.types = types;
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public Type[] getTypes() {
        return types;
    }

    public Move[] getMoves() {
        return moves;
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
}
