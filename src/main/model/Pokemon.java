package model;

import java.util.*;

public class Pokemon {
    private String name;
    private List<Type> types;
    private List<Move> moves;

    // REQUIRES: name.length() <= 16
    //           types.length <= 2 with no duplicates
    //           moves.length <= 4 with no duplicates
    // EFFECTS: Constructs a Pokémon with name, types, and moves
    public Pokemon(String name, List<Type> types, List<Move> moves) {
        this.name = name;
        this.types = types;
        this.moves = moves;
    }

    public String getName() {
        return this.name;
    }

    public List<Type> getTypes() {
        return this.types;
    }

    public List<Move> getMoves() {
        return this.moves;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    @Override
    public String toString() {
        String typesStr = "";
        for (Type type : this.types) {
            typesStr += type.name() + " ".repeat(16 - type.name().length());
        }
        return this.name + " ".repeat(16 - this.name.length()) + typesStr;
    }

    public String analyzePokemon() {
        return this + "\n"
                + "Multipliers when attacked by moves of type:\n"
                + Type.defensiveMultipliers(this.types) + "\n"
                + "Moves:\n"
                + this.movesToString() + "\n"
                + "Your moveset is very effective against:\n"
                + this.analyzeMoves().get("strongAgainst") + "\n"
                + "Your moveset has normal effectiveness against:\n"
                + this.analyzeMoves().get("normalAgainst") + "\n"
                + "Your moveset is not very effective against:\n"
                + this.analyzeMoves().get("weakAgainst") + "\n"
                + "Your moveset has no effect against:\n"
                + this.analyzeMoves().get("noEffectAgainst");
    }

    private String movesToString() {
        String movesStr = "";
        for (Move m : this.moves) {
            movesStr += m + "\n";
        }
        return movesStr.trim();
    }

    private HashMap<String, Set<Type>> analyzeMoves() {
        Set<Type> normalAgainst = new HashSet<>();
        Set<Type> strongAgainst = new HashSet<>();
        Set<Type> weakAgainst = new HashSet<>();
        Set<Type> noEffectAgainst = new HashSet<>();
        for (Move m : this.moves) {
            if (!m.isStatus()) {
                normalAgainst.addAll(m.getType().normalAgainst());
                strongAgainst.addAll(m.getType().strongAgainst());
                weakAgainst.addAll(m.getType().weakAgainst());
                noEffectAgainst.addAll(m.getType().noEffectAgainst());
            }
        }
        // if one move is strong, the moveset is strong
        normalAgainst.removeAll(strongAgainst);
        weakAgainst.removeAll(strongAgainst);
        noEffectAgainst.removeAll(strongAgainst);
        // if one move is normal, the moveset is normal
        weakAgainst.removeAll(normalAgainst);
        noEffectAgainst.removeAll(normalAgainst);
        // if one move is weak, the moveset is weak
        noEffectAgainst.removeAll(weakAgainst);
        return new HashMap<>(Map.of(
                "normalAgainst", normalAgainst,
                "strongAgainst", strongAgainst,
                "weakAgainst", weakAgainst,
                "noEffectAgainst", noEffectAgainst));
    }
}
