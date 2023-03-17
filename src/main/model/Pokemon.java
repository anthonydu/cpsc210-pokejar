package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Pokemon with a name, a list of Types and a list of Moves.
 *
 * @author Anthony Du
 */
public class Pokemon {
    private String name;
    private List<Type> types;
    private List<Move> moves;

    public Pokemon() {
        this.name = "";
        this.types = new ArrayList<>();
        this.moves = new ArrayList<>();
    }

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
     *
     * @param name the name to set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the Types of this Pokemon.
     *
     * @param types the Types to set to
     */
    public void setTypes(List<Type> types) {
        this.types = types;
    }

    /**
     * Sets the moveset of this Pokemon.
     *
     * @param moves the moveset to set to
     */
    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    @Override
    public boolean equals(Object o) {
        Pokemon pokemon = (Pokemon) o;
        return name.equals(pokemon.name) && types.equals(pokemon.types) && moves.equals(pokemon.moves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, types, moves);
    }

    /**
     * Generates a String that represents this Pokemon.
     *
     * @return a String that represents this Pokemon
     */
    @Override
    public String toString() {
        String str = StringUtil.fixCharCount(this.name, 15) + " ";
        for (Type type : this.types) {
            str += StringUtil.fixCharCount(type.name(), 11) + " ";
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
