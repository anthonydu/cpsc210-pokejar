package util;

import model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonList {
    /**
     * List of Pokemon in this Box
     */
    protected List<Pokemon> pokemons;

    /**
     * Constructs a PokemonList with an empty list of Pokemon
     */
    public PokemonList() {
        this(new ArrayList<>());
    }

    /**
     * Constructs a PokemonList with an existing list of Pokemon
     *
     * @param pokemons a list of Pokemon to initialize the Team
     */
    public PokemonList(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    /**
     * Returns a list of Pokemon that are in this Box
     *
     * @return a list of Pokemon that are in this Box
     */
    public List<Pokemon> get() {
        return this.pokemons;
    }

    /**
     * Gets the Pokemon at a specific index
     *
     * @param index the index to get the Pokemon from
     * @return the Pokemon at the specific index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public Pokemon get(int index) throws IndexOutOfBoundsException {
        return this.pokemons.get(index);
    }

    /**
     * Adds a Pokemon to this Box
     *
     * MODIFIES: this
     *
     * @param pokemon the Pokemon to add to this Box
     * @throws IllegalArgumentException if this Box already contains the Pokemon that is passed in
     */
    public void add(Pokemon pokemon) throws IllegalArgumentException {
        if (this.get().contains(pokemon)) {
            throw new IllegalArgumentException("Pokémon already in " + this.getClass().getSimpleName() + "!");
        }
        this.pokemons.add(pokemon);
    }

    /**
     * Removes a Pokemon from this Box
     *
     * MODIFIES: this
     *
     * @param pokemon the Pokemon to remove
     */
    public void remove(Pokemon pokemon) {
        this.pokemons.remove(pokemon);
    }
}
