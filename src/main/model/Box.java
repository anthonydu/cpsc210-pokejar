package model;

import java.util.List;
import java.util.ArrayList;

public class Box {
    private List<Pokemon> pokemons;

    // EFFECTS:  Constructs an empty Box
    public Box() {
        this.pokemons = new ArrayList<>();
    }

    public Box(List<Pokemon> box) {
        this.pokemons = box;
    }

    public Pokemon getPokemon(int index) throws ArrayIndexOutOfBoundsException {
        return this.pokemons.get(index);
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
    public void removePokemon(Pokemon pokemon) {
        // index is used because Pokémon's attributes aren't unique
        this.pokemons.remove(pokemon);
    }

    // REQUIRES: this.pokemons.size() < 100
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.pokemons.size(); i++) {
            result += i + (i < 10 ? "  " : " ") + this.pokemons.get(i) + "\n";
        }
        return result.trim();
    }
}
