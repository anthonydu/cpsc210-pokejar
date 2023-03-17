package model;

import java.util.Objects;

/**
 * A Pokemon's Move with a name, a Type, and whether it's a status or attacking Move.
 *
 * @author Anthony Du
 */
public class Move {
    private String name;
    private Type type;
    private boolean isStatus;

    /**
     * Constructs a Move with name, types and whether it is status or attacking.
     *
     * @param name the name of this Move
     * @param type the type of this Move
     * @param isStatus whether this Move is status or attacking
     */
    public Move(String name, Type type, boolean isStatus) {
        this.name = name;
        this.type = type;
        this.isStatus = isStatus;
    }

    /**
     * Gets the name of this Move.
     *
     * @return the name of this Move
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the type of this Move.
     *
     * @return the type of this Move
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Gets whether this Move is status or attacking.
     *
     * @return whether this Move is status or attacking
     */
    public boolean isStatus() {
        return isStatus;
    }

    /**
     * Sets the name of this Move.
     *
     * @param name the name to set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the Type of this Move.
     *
     * @param type the Type to set to
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Sets whether this Move is status or attacking.
     *
     * @param isStatus true for status Move and false for attacking Move
     */
    public void setStatus(boolean isStatus) {
        this.isStatus = isStatus;
    }

    /**
     * Compares the specified object with this Move for equality.
     * Two Moves are equal if all their attributes are equal.
     *
     * @param o the object to be compared for equality with this Move
     * @return true if the specified object is equal to this Move and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        Move move = (Move) o;
        return isStatus == move.isStatus && name.equals(move.name) && type == move.type;
    }

    /**
     * Returns the hash code value for this Move.
     *
     * @return the hash code value for this Move
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, type, isStatus);
    }

    /**
     * Generates a String that represents this Move.
     *
     * @return a String that represents this Move
     */
    @Override
    public String toString() {
        String str = StringUtil.fixCharCount(this.name, 15) + " ";
        return str
                + StringUtil.fixCharCount(this.type.name(), 11) + " "
                + (this.isStatus ? "Status" : "Attacking");
    }
}
