package model;

import java.util.*;

/**
 * An enum of all Pokemon Types,
 * methods that give their strengths, weaknesses, and immunities,
 * and static methods for analyzing Types and working with Strings.
 *
 * @author Anthony Du
 */
public enum Type {
    NORMAL, FIRE, WATER, GRASS, ELECTRIC, ICE, FIGHTING, POISON, GROUND,
    FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY;

    /**
     * A map of every Type and their strengths.
     */
    private static final Map<Type, List<Type>> strengths = new LinkedHashMap<Type, List<Type>>() {
        {
            put(NORMAL, Arrays.asList());
            put(FIRE, Arrays.asList(FIRE, GRASS, ICE, BUG, STEEL, FAIRY));
            put(WATER, Arrays.asList(FIRE, WATER, ICE, STEEL));
            put(GRASS, Arrays.asList(WATER, GRASS, ELECTRIC, GROUND));
            put(ELECTRIC, Arrays.asList(ELECTRIC, FLYING, STEEL));
            put(ICE, Arrays.asList(ICE));
            put(FIGHTING, Arrays.asList(BUG, ROCK, DARK));
            put(POISON, Arrays.asList(GRASS, FIGHTING, POISON, BUG, FAIRY));
            put(GROUND, Arrays.asList(POISON, ROCK));
            put(FLYING, Arrays.asList(GRASS, FIGHTING, BUG));
            put(PSYCHIC, Arrays.asList(FIGHTING, PSYCHIC));
            put(BUG, Arrays.asList(GRASS, FIGHTING, GROUND));
            put(ROCK, Arrays.asList(NORMAL, FIRE, POISON, FLYING));
            put(GHOST, Arrays.asList(POISON, BUG));
            put(DRAGON, Arrays.asList(FIRE, WATER, GRASS, ELECTRIC));
            put(DARK, Arrays.asList(GHOST, DARK));
            put(STEEL, Arrays.asList(NORMAL, GRASS, ICE, FLYING, PSYCHIC, BUG, ROCK, DRAGON, STEEL, FAIRY));
            put(FAIRY, Arrays.asList(FIGHTING, BUG, DARK));
        }
    };

    /**
     * A map of every Type and their weaknesses.
     */
    private static final Map<Type, List<Type>> weaknesses = new LinkedHashMap<Type, List<Type>>() {
        {
            put(NORMAL, Arrays.asList(FIGHTING));
            put(FIRE, Arrays.asList(WATER, GROUND, ROCK));
            put(WATER, Arrays.asList(GRASS, ELECTRIC));
            put(GRASS, Arrays.asList(FIRE, ICE, POISON, FLYING, BUG));
            put(ELECTRIC, Arrays.asList(GROUND));
            put(ICE, Arrays.asList(FIRE, FIGHTING, ROCK, STEEL));
            put(FIGHTING, Arrays.asList(FLYING, PSYCHIC, FAIRY));
            put(POISON, Arrays.asList(GROUND, PSYCHIC));
            put(GROUND, Arrays.asList(WATER, GRASS, ICE));
            put(FLYING, Arrays.asList(ELECTRIC, ICE, ROCK));
            put(PSYCHIC, Arrays.asList(BUG, GHOST, DARK));
            put(BUG, Arrays.asList(FIRE, FLYING, ROCK));
            put(ROCK, Arrays.asList(WATER, GRASS, FIGHTING, GROUND));
            put(GHOST, Arrays.asList(GHOST, DARK));
            put(DRAGON, Arrays.asList(ICE, DRAGON, FAIRY));
            put(DARK, Arrays.asList(FIGHTING, BUG, FAIRY));
            put(STEEL, Arrays.asList(FIRE, FIGHTING, GROUND));
            put(FAIRY, Arrays.asList(POISON, STEEL));
        }
    };

    /**
     * A map of every Type and their immunities.
     */
    private static final Map<Type, List<Type>> immunities = new LinkedHashMap<Type, List<Type>>() {
        {
            put(NORMAL, Arrays.asList(GHOST));
            put(FIRE, Arrays.asList());
            put(WATER, Arrays.asList());
            put(GRASS, Arrays.asList());
            put(ELECTRIC, Arrays.asList());
            put(ICE, Arrays.asList());
            put(FIGHTING, Arrays.asList());
            put(POISON, Arrays.asList());
            put(GROUND, Arrays.asList(ELECTRIC));
            put(FLYING, Arrays.asList(GROUND));
            put(PSYCHIC, Arrays.asList());
            put(BUG, Arrays.asList());
            put(ROCK, Arrays.asList());
            put(GHOST, Arrays.asList(NORMAL, FIGHTING));
            put(DRAGON, Arrays.asList());
            put(DARK, Arrays.asList(PSYCHIC));
            put(STEEL, Arrays.asList(POISON));
            put(FAIRY, Arrays.asList(DRAGON));
        }
    };

    /**
     * Returns a list of Types that this Type receives 0.5x damage from.
     *
     * @return a list of Types that this Type receives 0.5x damage from
     */
    public List<Type> strengths() {
        return strengths.get(this);
    }

    /**
     * Returns a list of Types that this Type receives 2x damage from.
     *
     * @return a list of Types that this Type receives 2x damage from
     */
    public List<Type> weaknesses() {
        return weaknesses.get(this);
    }

    /**
     * Returns a list of Types that this Type receives 0x damage from.
     *
     * @return a list of Types that this Type receives 0x damage from
     */
    public List<Type> immunities() {
        return immunities.get(this);
    }

    /**
     * Returns a list of Types that receives 1x damage from this Type.
     *
     * @return a list of Types that receives 1x damage from this Type
     */
    public List<Type> normalAgainst() {
        List<Type> types = new ArrayList<>(Arrays.asList(Type.values()));
        for (Type t : Type.values()) {
            if (t.strengths().contains(this) || t.weaknesses().contains(this) || t.immunities().contains(this)) {
                types.remove(t);
            }
        }
        return types;
    }

    /**
     * Returns a list of Types that receives 2x damage from this Type.
     *
     * @return a list of Types that receives 2x damage from this Type
     */
    public List<Type> strongAgainst() {
        List<Type> types = new ArrayList<>();
        for (Type t : Type.values()) {
            if (t.weaknesses().contains(this)) {
                types.add(t);
            }
        }
        return types;
    }

    /**
     * Returns a list of Types that receives 0.5x damage from this Type.
     *
     * @return a list of Types that receives 0.5x damage from this Type
     */
    public List<Type> weakAgainst() {
        List<Type> types = new ArrayList<>();
        for (Type t : Type.values()) {
            if (t.strengths().contains(this)) {
                types.add(t);
            }
        }
        return types;
    }

    /**
     * Returns a list of Types that receives 0x damage from this Type.
     *
     * @return a list of Types that receives 0x damage from this Type
     */
    public List<Type> noEffectAgainst() {
        List<Type> types = new ArrayList<>();
        for (Type t : Type.values()) {
            if (t.immunities().contains(this)) {
                types.add(t);
            }
        }
        return types;
    }

    /**
     * Returns a Map of multipliers when this Type is attacked by each Type.
     *
     * @return a Map of multipliers when this Type is attacked by each Type
     */
    public Map<Type, Double> defensiveMultipliers() {
        Map<Type, Double> multipliers = new LinkedHashMap<>();
        for (Type t : Type.values()) {
            if (this.strengths().contains(t)) {
                multipliers.put(t, 0.5);
            } else if (this.weaknesses().contains(t)) {
                multipliers.put(t, 2.0);
            } else if (this.immunities().contains(t)) {
                multipliers.put(t, 0.0);
            } else {
                multipliers.put(t, 1.0);
            }
        }
        return multipliers;
    }

    /**
     * Returns a Map of combined multipliers when a set of arbitrary number of Types are attacked by each Type.
     *
     * @param types a list of Types of arbitrary size
     * @return a Map of combined multipliers when a set of arbitrary number of Types are attacked by each Type
     */
    public static Map<Type, Double> defensiveMultipliers(List<Type> types) {
        Map<Type, Double> multipliers = new LinkedHashMap<>();
        for (Type t : Type.values()) {
            Double multiplier = 1.0;
            for (int i = 0; i < types.size(); i++) {
                multiplier *= types.get(i).defensiveMultipliers().get(t);
            }
            multipliers.put(t, multiplier);
        }
        return multipliers;
    }

    /**
     * Returns a Map of multipliers when this Type attacks each Type.
     *
     * @return a Map of multipliers when this Type attacks each Type
     */
    public Map<Type, Double> offensiveMultipliers() {
        Map<Type, Double> multipliers = new LinkedHashMap<>();
        for (Type t : Type.values()) {
            if (this.strongAgainst().contains(t)) {
                multipliers.put(t, 2.0);
            } else if (this.weakAgainst().contains(t)) {
                multipliers.put(t, 0.5);
            } else if (this.noEffectAgainst().contains(t)) {
                multipliers.put(t, 0.0);
            } else {
                multipliers.put(t, 1.0);
            }
        }
        return multipliers;
    }

    /**
     * Returns a Map of the max multiplier a list of types has on each type.
     *
     * @param types a list of Types of arbitrary size
     * @return a Map of the max multiplier a list of types has on each type
     */
    public static Map<Type, Double> offensiveMultipliers(List<Type> types) {
        Map<Type, List<Double>> allMultipliers = new LinkedHashMap<>();
        for (Type t : Type.values()) {
            allMultipliers.put(t, new ArrayList<>());
        }
        for (Type offenseType : types) {
            for (Type defenseType : Type.values()) {
                allMultipliers.get(defenseType).add(offenseType.offensiveMultipliers().get(defenseType));
            }
        }
        Map<Type, Double> maxMultipliers = new LinkedHashMap<>();
        for (Type t : Type.values()) {
            maxMultipliers.put(t, Collections.max(allMultipliers.get(t)));
        }
        return maxMultipliers;
    }

    /**
     * Parses a Type from a String.
     *
     * @param str a String that has the name of a Type
     * @return a Type parsed from a String
     * @throws IllegalArgumentException if the String cannot be parsed to Type
     */
    public static Type fromString(String str) throws IllegalArgumentException {
        switch (str.toLowerCase()) {
            case "normal": return NORMAL;
            case "fire": return FIRE;
            case "water": return WATER;
            case "grass": return GRASS;
            case "electric": return ELECTRIC;
            case "ice": return ICE;
            case "fighting": return FIGHTING;
            case "poison": return POISON;
            case "ground": return GROUND;
            case "flying": return FLYING;
            case "psychic": return PSYCHIC;
            case "bug": return BUG;
            case "rock": return ROCK;
            case "ghost": return GHOST;
            case "dragon": return DRAGON;
            case "dark": return DARK;
            case "steel": return STEEL;
            case "fairy": return FAIRY;
            default: throw new IllegalArgumentException("Invalid type!");
        }
    }

    /**
     * Return a list of all Types parsed from a list of Strings.
     *
     * @param strs a list of strings that contains Type names
     * @return a list of all Types parsed from a list of Strings
     */
    public static List<Type> fromListOfStrings(List<String> strs) throws IllegalArgumentException {
        List<Type> types = new ArrayList<>();
        for (String s : strs) {
            if (types.contains(Type.fromString(s))) {
                throw new IllegalArgumentException("Duplicate type found!");
            } else {
                types.add(Type.fromString(s));
            }

        }
        return types;
    }
}
