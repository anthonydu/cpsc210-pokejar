package model;

import java.util.*;

/**
 * A Pokemon with a name, a list of Types and a list of Moves.
 *
 * @author Anthony Du
 */
public class Pokemon {
    private String name;
    private List<Type> types;
    private List<Move> moves;

    /**
     * Constructs a Pokemon with name, Types, and moves.
     *
     * @param name the name of this Pokemon
     * @param types the Types of this Pokemon
     * @param moves the moveset of this Pokemon
     */
    public Pokemon(String name, List<Type> types, List<Move> moves) {
        this.name = name;
        this.types = types;
        this.moves = moves;
    }

    /**
     * Gets the name of this Pokemon.
     *
     * @return the name of this Pokemon
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the Types of this Pokemon.
     *
     * @return the Types of this Pokemon
     */
    public List<Type> getTypes() {
        return this.types;
    }

    /**
     * Gets the moveset of this Pokemon.
     *
     * @return the moveset of this Pokemon
     */
    public List<Move> getMoves() {
        return this.moves;
    }

    /**
     * Sets the name of this Pokemon.
     * <p>
     * MODIFIES: this
     *
     * @param name the name to set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the Types of this Pokemon.
     * <p>
     * MODIFIES: this
     *
     * @param types the Types to set to
     */
    public void setTypes(List<Type> types) {
        this.types = types;
    }

    /**
     * Sets the moveset of this Pokemon.
     * <p>
     * MODIFIES: this
     *
     * @param moves the moveset to set to
     */
    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    /**
     * Generates a String that represents this Pokemon.
     *
     * @return a String that represents this Pokemon
     */
    @Override
    public String toString() {
        String str = "";
        if (this.name.length() >= 16) {
            str += this.name.substring(0, 14) + "… ";
        } else {
            str += this.name + String.join("", Collections.nCopies(16 - this.name.length(), " "));
        }
        for (Type type : this.types) {
            str += type.name() + String.join("", Collections.nCopies(16 - type.name().length(), " "));
        }
        return str.trim();
    }

    /**
     * Generates a list of types in this Pokemon's moveset.
     *
     * @return a list of types in this Pokemon's moveset
     */
    public List<Type> attackingMoveTypes() {
        List<Type> moveTypes = new ArrayList<>();
        for (Move m : this.getMoves()) {
            if (!m.isStatus()) {
                moveTypes.add(m.getType());
            }
        }
        return moveTypes;
    }
}
