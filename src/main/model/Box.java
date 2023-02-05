package model;

import java.util.List;
import java.util.ArrayList;

public class Box {
    private List<Pokemon> pokemons;

    public Box() {
        this(new ArrayList<>());
    }

    public Box(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public List<Pokemon> get() {
        return this.pokemons;
    }

    public Pokemon get(int index) throws ArrayIndexOutOfBoundsException {
        return this.pokemons.get(index);
    }

    // MODIFIES: this
    // EFFECTS:  Add a Pokémon to the box
    public void add(Pokemon pokemon) {
        this.pokemons.add(pokemon);
    }

    // REQUIRES: 0 <= index < pokemons.size()
    // MODIFIES: this
    // EFFECTS:  Remove a Pokémon from the box
    public void remove(Pokemon pokemon) {
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
