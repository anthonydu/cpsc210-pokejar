package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Box that holds an arbitrary number of Pokemon.
 *
 * @author Anthony Du
 */
public class Box extends ArrayList<Pokemon> {

    /**
     * Constructs an empty Box.
     */
    public Box() {
        super();
    }

    /**
     * Constructs a Box with an existing list of Pokemon.
     *
     * @param pokemons a list of Pokemon to initialize the Box
     * @throws IllegalArgumentException if pokemons contains multiple Pokemon with the same name
     */
    public Box(List<Pokemon> pokemons) {
        this.addAll(pokemons);
    }

    /**
     * Returns the Pokemon that equals to the Pokemon that is passed in.
     *
     * @param pokemon the Pokemon to get
     * @return the Pokemon that equals to the Pokemon that is passed in
     */
    public Pokemon get(Pokemon pokemon) {
        for (Pokemon p : this) {
            if (p.equals(pokemon)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Generates a String that represents this Box.
     *
     * @return a String that represents this Box
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.size(); i++) {
            result += i + " " + this.get(i) + "\n";
        }
        return result.trim();
    }
}
