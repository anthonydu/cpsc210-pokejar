package model;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a Box that holds an arbitrary number of Pokémon
 *
 * @author Anthony Du
 */
public class Box {
    /**
     * List of Pokémon in this Box
     */
    private List<Pokemon> pokemons;

    /**
     * Constructs a Box with an empty list of Pokémon
     */
    public Box() {
        this(new ArrayList<>());
    }

    /**
     * Constructs a Box with an existing list of Pokémon
     *
     * @param pokemons a list of Pokémon to initialize the Team
     */
    public Box(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    /**
     * Returns a list of Pokémon that are in this Box
     *
     * @return a list of Pokémon that are in this Box
     */
    public List<Pokemon> get() {
        return this.pokemons;
    }

    /**
     * Gets the Pokémon at a specific index
     *
     * REQUIRES: 0 <= index < pokemons.size()
     *
     * @param index the index to get the Pokémon from
     * @return the Pokémon at the specific index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public Pokemon get(int index) throws IndexOutOfBoundsException {
        return this.pokemons.get(index);
    }

    /**
     * Adds a Pokémon to this Box
     *
     * REQUIRES: this.get().contains(pokemon) == false
     * MODIFIES: this
     *
     * @param pokemon the Pokémon to add to this Box
     * @throws IllegalArgumentException if this Box already contains the Pokémon that is passed in
     */
    public void add(Pokemon pokemon) throws IllegalArgumentException {
        if (this.get().contains(pokemon)) {
            throw new IllegalArgumentException("Pokémon already in " + this.getClass().getSimpleName() + "!");
        }
        this.pokemons.add(pokemon);
    }

    /**
     * Removes a Pokémon from this Box
     *
     * REQUIRES: 0 <= index < pokemons.size()
     * MODIFIES: this
     *
     * @param pokemon the Pokémon to remove
     */
    public void remove(Pokemon pokemon) {
        // index is used because Pokémon's attributes aren't unique
        this.pokemons.remove(pokemon);
    }

    /**
     * Returns a String that represents this Box
     *
     * @return a String that represents this Box
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.pokemons.size(); i++) {
            result += i + (i < 10 ? "  " : " ") + this.pokemons.get(i) + "\n";
        }
        return result.trim();
    }
}
