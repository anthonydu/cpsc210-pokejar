package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Team with a name and holds a list of Pokémon
 *
 * @author Anthony Du
 */
public class Team extends Box {
    /**
     * The name of this Team
     */
    private String name;

    /**
     * Constructs a Team with an empty list of Pokémon
     *
     * @param name the name of this Team
     */
    public Team(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * Constructs a Team with an existing list of Pokémon
     *
     * @param name the name of this Team
     * @param pokemons a list of Pokémon to initialize the Team
     */
    public Team(String name, List<Pokemon> pokemons) {
        super(pokemons);
        this.name = name;
    }

    /**
     * Returns a String that represents this Team
     *
     * @return a String that represents this Team
     */
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

    /**
     * Returns a String that represents the analysis of this Team
     *
     * @return a String that represents the analysis of this Team
     */
    public String analyze() {
        return "team analyzed";
    }
}
