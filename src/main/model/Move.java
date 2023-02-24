package model;

import java.util.*;

/**
 * Represents a Pokemon's Move with a name, a Type, and whether it's a status or attacking Move
 *
 * @author Anthony Du
 */
public class Move {
    private String name;
    private Type type;
    private boolean isStatus;

    /**
     * Constructs a Move with name, types and whether it is status or attacking
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
     * Returns the name of this Move
     *
     * @return the name of this Move
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the type of this Move
     *
     * @return the type of this Move
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Returns whether this Move is status or attacking
     *
     * @return whether this Move is status or attacking
     */
    public boolean isStatus() {
        return isStatus;
    }

    /**
     * Sets the name of this Move
     *
     * MODIFIES: this
     *
     * @param name the name to set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the Type of this Move
     *
     * MODIFIES: this
     *
     * @param type the Type to set to
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Sets whether this Move is status or attacking
     *
     * MODIFIES: this
     *
     * @param isStatus true for status Move and false for attacking Move
     */
    public void setStatus(boolean isStatus) {
        this.isStatus = isStatus;
    }

    /**
     * Returns a String that represents this Move
     *
     * @return a String that represents this Move
     */
    @Override
    public String toString() {
        String str = "";
        if (this.name.length() >= 16) {
            str += this.name.substring(0, 14) + "… ";
        } else {
            str += this.name + String.join("", Collections.nCopies(16 - this.name.length(), " "));
        }
        return str
                + this.type.name() + String.join("", Collections.nCopies(16 - this.type.name().length(), " "))
                + (this.isStatus ? "Status" : "Attacking");
    }
}
