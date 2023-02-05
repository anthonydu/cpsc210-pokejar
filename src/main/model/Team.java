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
    public String toString() {
        String pokemonsStr = "";
        for (Pokemon p : this.get()) {
            pokemonsStr += p.getName() + " ".repeat(16 - p.getName().length());
        }
        return this.name + " ".repeat(16 - name.length())
                + pokemonsStr;
    }

    public String analyze() {
        return "team analyzed";
    }
}
