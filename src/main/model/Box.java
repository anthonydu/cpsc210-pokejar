package model;

import util.PokemonList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Box that holds an arbitrary number of Pokémon
 *
 * @author Anthony Du
 */
public class Box extends PokemonList {

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
        super(pokemons);
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
            result += i + " " + this.pokemons.get(i) + "\n";
        }
        return result.trim();
    }
}
