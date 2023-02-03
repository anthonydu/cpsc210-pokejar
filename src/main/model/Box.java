package model;

import java.util.List;
import java.util.ArrayList;

public class Box {
    private List<Pokemon> pokemons;

    // EFFECTS:  Constructs an empty Box
    public Box() {
        this.pokemons = new ArrayList<>();
    }

    public List<Pokemon> getPokemons() {
        return this.pokemons;
    }

    // MODIFIES: this
    // EFFECTS:  Add a Pokémon to the box
    public void addPokemon(Pokemon pokemon) {
        this.pokemons.add(pokemon);
    }

    // REQUIRES: 0 <= index < pokemons.size()
    // MODIFIES: this
    // EFFECTS:  Remove a Pokémon from the box
    public void removePokemon(int index) {
        // index is used because Pokémon's attributes aren't unique
        this.pokemons.remove(index);
    }
}
