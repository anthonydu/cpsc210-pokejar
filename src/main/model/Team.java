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
        if (this.name.length() >= 16) {
            str += this.name.substring(0, 14) + "… ";
        } else {
            str += this.name + " ".repeat(16 - this.name.length());
        }
        for (Pokemon p : this.get()) {
            str += p.getName() + " ".repeat(16 - p.getName().length());
        }
        return str;
    }

    public String analyze() {
        return "team analyzed";
    }
}
