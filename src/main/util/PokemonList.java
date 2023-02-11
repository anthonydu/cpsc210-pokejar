package util;

import model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonList {
    /**
     * List of Pokémon in this Box
     */
    protected List<Pokemon> pokemons;

    /**
     * Constructs a PokemonList with an empty list of Pokémon
     */
    public PokemonList() {
        this(new ArrayList<>());
    }

    /**
     * Constructs a PokemonList with an existing list of Pokémon
     *
     * @param pokemons a list of Pokémon to initialize the Team
     */
    public PokemonList(List<Pokemon> pokemons) {
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
     * MODIFIES: this
     *
     * @param pokemon the Pokémon to remove
     */
    public void remove(Pokemon pokemon) {
        // index is used because Pokémon's attributes aren't unique
        this.pokemons.remove(pokemon);
    }
}