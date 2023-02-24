package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Box that holds an arbitrary number of Pokemon
 *
 * @author Anthony Du
 */
public class Box extends ArrayList<Pokemon> {

    public Box() {
        super();
    }

    /**
     * Constructs a Box with an existing list of Pokemon
     *
     * @param pokemons a list of Pokemon to initialize the Team
     */
    public Box(List<Pokemon> pokemons) {
        this.addAll(pokemons);
    }

    /**
     * Adds a Pokemon to this Box
     *
     * MODIFIES: this
     *
     * @param pokemon the Pokemon to add to this Box
     * @return true if Pokemon is added successfully
     * @throws IllegalArgumentException if this Box already contains the Pokemon that is passed in
     */
    @Override
    public boolean add(Pokemon pokemon) throws IllegalArgumentException {
        if (this.contains(pokemon)) {
            throw new IllegalArgumentException("Pokémon already in box!");
        }
        super.add(pokemon);
        return true;
    }

    /**
     * Returns a String that represents this Box
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
