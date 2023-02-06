package model;

import java.util.ArrayList;
import java.util.List;

public class Team extends Box {
    private String name;

    public Team(String name) {
        this(name, new ArrayList<>());
    }

    public Team(String name, List<Pokemon> pokemons) {
        super(pokemons);
        this.name = name;
    }

    @Override
    public void add(Pokemon pokemon) throws IllegalArgumentException {
        if (this.get().contains(pokemon)) {
            throw new IllegalArgumentException("Pokémon already in list!");
        }
        super.add(pokemon);
    }

    @Override
    public String toString() {
        String str = "";
        for (Pokemon p : this.get()) {
            str += p.getName() + " ".repeat(16 - p.getName().length());
        }
        return this.name + " ".repeat(16 - name.length()) + str;
    }

    public String analyze() {
        return "team analyzed";
    }
}
