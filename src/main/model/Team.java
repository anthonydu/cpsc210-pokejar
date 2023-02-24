package model;

import org.json.JSONObject;
import util.PokemonList;
import java.util.*;

/**
 * Represents a Team with a name and holds a list of Pokemon
 *
 * @author Anthony Du
 */
public class Team extends PokemonList {
    /**
     * The name of this Team
     */
    private String name;

    /**
     * Constructs a Team with an empty list of Pokemon
     *
     * @param name the name of this Team
     */
    public Team(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * Constructs a Team with an existing list of Pokemon
     *
     * @param name the name of this Team
     * @param pokemons a list of Pokemon to initialize the Team
     */
    public Team(String name, List<Pokemon> pokemons) {
        super(pokemons);
        this.name = name;
    }

    /**
     * Returns the name of this Team
     *
     * @return the name of this Team
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this Team
     *
     * @param name the name to set to
     */
    public void setName(String name) {
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
            str += this.name + String.join("", Collections.nCopies(16 - this.name.length(), " "));
        }
        for (Pokemon p : this.get()) {
            str += p.getName() + String.join("", Collections.nCopies(16 - p.getName().length(), " "));
        }
        return str.trim();
    }

    /**
     * Returns a JSONObject that represents this Team
     *
     * @return a JSONObject that represents this Team
     */
    @Override
    public JSONObject toJson() {
        return super.toJson().put("name", this.name);
    }

    /**
     * Generates a map of the number of Pokemon in this Team that is weak to or resist each Type depending on mode
     *
     * @param mode either "weak" or "resist"
     * @return a map of the number of Pokemon in this Team that is weak to or resist each Type depending on mode
     */
    public Map<Type, Integer> numberOfWeakOrResist(String mode) {
        if (mode != "weak" && mode != "resist") {
            throw new IllegalArgumentException("Invalid mode, must be \"weak\" or \"resist\"!");
        }
        Map<Type, Integer> total = new LinkedHashMap<>();
        for (Type t : Type.values()) {
            total.put(t, 0);
        }
        for (Pokemon p : this.allDefensiveMultipliers().keySet()) {
            for (Type t : Type.values()) {
                if (mode == "weak" && this.allDefensiveMultipliers().get(p).get(t) > 1.0) {
                    total.put(t, total.get(t) + 1);
                } else if (mode == "resist" && this.allDefensiveMultipliers().get(p).get(t) < 1.0) {
                    total.put(t, total.get(t) + 1);
                }
            }
        }
        return total;
    }

    /**
     * Returns a map of each Pokemon's defensive multipliers
     *
     * @return a map of each Pokemon's defensive multipliers
     */
    public Map<Pokemon, Map<Type, Double>> allDefensiveMultipliers() {
        Map<Pokemon, Map<Type, Double>> allMultipliers = new LinkedHashMap<>();
        for (Pokemon p : pokemons) {
            allMultipliers.put(p, Type.defensiveMultipliers(p.getTypes()));
        }
        return allMultipliers;
    }
}
