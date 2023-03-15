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
    public Box(List<Pokemon> pokemons) throws IllegalArgumentException {
        for (Pokemon pokemon : pokemons) {
            if (!this.add(pokemon)) {
                throw new IllegalArgumentException("Box cannot contain multiple Pokémon with the same name!");
            }
        }
    }

    /**
     * Adds a Pokemon to this Box.
     * <p>
     * MODIFIES: this
     *
     * @param pokemon the Pokemon to add to this Box
     * @return true if Pokemon is added successfully
     */
    @Override
    public boolean add(Pokemon pokemon) {
        for (Pokemon p : this) {
            if (p.getName().equals(pokemon.getName())) {
                return false;
            }
        }
        super.add(pokemon);
        return true;
    }

    /**
     * Returns the index of a Pokemon with the specified name.
     *
     * @param name the name of the Pokemon to find
     * @return the index of a Pokemon with the specified name
     */
    public int indexOf(String name) {
        for (Pokemon p : this) {
            if (p.getName().equals(name)) {
                return indexOf(p);
            }
        }
        return -1;
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
