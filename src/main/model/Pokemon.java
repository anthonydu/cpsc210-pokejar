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
            str += this.name + String.join("", Collections.nCopies(16 - this.name.length(), " "));
        }
        for (Type type : this.types) {
            str += type.name() + String.join("", Collections.nCopies(16 - type.name().length(), " "));
        }
        return str.trim();
    }
}
