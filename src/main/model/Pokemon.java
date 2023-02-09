package model;

import java.util.*;

/**
 * Represents a Pokémon with a name, a list of Types and a list of Moves
 *
 * @author Anthony Du
 */
public class Pokemon {
    /**
     * The name of this Pokémon
     */
    private String name;
    /**
     * The Types of this Pokémon
     */
    private List<Type> types;
    /**
     * The moveset of this Pokémon
     */
    private List<Move> moves;

    /**
     * Constructs a Pokémon with name, Types, and moves
     *
     * @param name the name of this Pokémon
     * @param types the Types of this Pokémon
     * @param moves the moveset of this Pokémon
     */
    public Pokemon(String name, List<Type> types, List<Move> moves) {
        this.name = name;
        this.types = types;
        this.moves = moves;
    }

    /**
     * Returns the name of this Pokémon
     *
     * @return the name of this Pokémon
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the Types of this Pokémon
     *
     * @return the Types of this Pokémon
     */
    public List<Type> getTypes() {
        return this.types;
    }

    /**
     * Returns the moveset of this Pokémon
     *
     * @return the moveset of this Pokémon
     */
    public List<Move> getMoves() {
        return this.moves;
    }

    /**
     * Sets the name of this Pokémon
     *
     * @param name the name to set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the Types of this Pokémon
     *
     * @param types the Types to set to
     */
    public void setTypes(List<Type> types) {
        this.types = types;
    }

    /**
     * Sets the moveset of this Pokémon
     *
     * @param moves the moveset to set to
     */
    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    /**
     * Returns a String that represents this Pokémon
     *
     * @return a String that represents this Pokémon
     */
    @Override
    public String toString() {
        String str = "";
        if (this.name.length() >= 16) {
            str += this.name.substring(0, 14) + "… ";
        } else {
            str += this.name + " ".repeat(16 - this.name.length());
        }
        for (Type type : this.types) {
            str += type.name() + " ".repeat(16 - type.name().length());
        }
        return str;
    }

    /**
     * Returns a String that represents the analysis of this Pokémon
     *
     * @return a String that represents the analysis of this Pokémon
     */
    public String analyze() {
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

    /**
     * Returns a String that represents the moveset of this Pokémon
     *
     * @return a String that represents the moveset of this Pokémon
     */
    private String movesToString() {
        String movesStr = "";
        for (Move m : this.moves) {
            movesStr += m + "\n";
        }
        return movesStr.trim();
    }

    /**
     * Generates a Map of that's an analysis of the moveset of this Pokémon
     *
     * @return a Map of that's an analysis of the moveset of this Pokémon
     */
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
