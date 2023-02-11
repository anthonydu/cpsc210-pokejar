package model;

import java.util.*;

/**
 * Contains all Pokémon Types,
 * methods that give their strengths, weaknesses, and immunities,
 * and static methods for analysis and working with Strings.
 *
 * @author Anthony Du
 */
public enum Type {
    NORMAL, FIRE, WATER, GRASS, ELECTRIC, ICE, FIGHTING, POISON, GROUND,
    FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY;

    /**
     * Returns a list of Types that this Type receives 0.5x damage from
     *
     * @return a list of Types that this Type receives 0.5x damage from
     * @throws IllegalStateException if this Type is not handled by this method
     */
    public List<Type> strengths() throws IllegalStateException {
        switch (this) {
            case NORMAL:    return Arrays.asList();
            case FIRE:      return Arrays.asList(FIRE, GRASS, ICE, BUG, STEEL, FAIRY);
            case WATER:     return Arrays.asList(FIRE, WATER, ICE, STEEL);
            case GRASS:     return Arrays.asList(WATER, GRASS, ELECTRIC, GROUND);
            case ELECTRIC:  return Arrays.asList(ELECTRIC, FLYING, STEEL);
            case ICE:       return Arrays.asList(ICE);
            case FIGHTING:  return Arrays.asList(BUG, ROCK, DARK);
            case POISON:    return Arrays.asList(GRASS, FIGHTING, POISON, BUG, FAIRY);
            case GROUND:    return Arrays.asList(POISON, ROCK);
            case FLYING:    return Arrays.asList(GRASS, FIGHTING, BUG);
            case PSYCHIC:   return Arrays.asList(FIGHTING, PSYCHIC);
            case BUG:       return Arrays.asList(GRASS, FIGHTING, GROUND);
            case ROCK:      return Arrays.asList(NORMAL, FIRE, POISON, FLYING);
            case GHOST:     return Arrays.asList(POISON, BUG);
            case DRAGON:    return Arrays.asList(FIRE, WATER, GRASS, ELECTRIC);
            case DARK:      return Arrays.asList(GHOST, DARK);
            case STEEL:     return Arrays.asList(NORMAL, GRASS, ICE, FLYING, PSYCHIC, BUG, ROCK, DRAGON, STEEL, FAIRY);
            case FAIRY:     return Arrays.asList(FIGHTING, BUG, DARK);
            default:        throw new IllegalStateException();
        }
    }

    /**
     * Returns a list of Types that this Type receives 2x damage from
     *
     * @return a list of Types that this Type receives 2x damage from
     * @throws IllegalStateException if this Type is not handled by this method
     */
    public List<Type> weaknesses() throws IllegalStateException {
        switch (this) {
            case NORMAL:    return Arrays.asList(FIGHTING);
            case FIRE:      return Arrays.asList(WATER, GROUND, ROCK);
            case WATER:     return Arrays.asList(GRASS, ELECTRIC);
            case GRASS:     return Arrays.asList(FIRE, ICE, POISON, FLYING, BUG);
            case ELECTRIC:  return Arrays.asList(GROUND);
            case ICE:       return Arrays.asList(FIRE, FIGHTING, ROCK, STEEL);
            case FIGHTING:  return Arrays.asList(FLYING, PSYCHIC, FAIRY);
            case POISON:    return Arrays.asList(GROUND, PSYCHIC);
            case GROUND:    return Arrays.asList(WATER, GRASS, ICE);
            case FLYING:    return Arrays.asList(ELECTRIC, ICE, ROCK);
            case PSYCHIC:   return Arrays.asList(BUG, GHOST, DARK);
            case BUG:       return Arrays.asList(FIRE, FLYING, ROCK);
            case ROCK:      return Arrays.asList(WATER, GRASS, FIGHTING, GROUND);
            case GHOST:     return Arrays.asList(GHOST, DARK);
            case DRAGON:    return Arrays.asList(ICE, DRAGON, FAIRY);
            case DARK:      return Arrays.asList(FIGHTING, BUG, FAIRY);
            case STEEL:     return Arrays.asList(FIRE, FIGHTING, GROUND);
            case FAIRY:     return Arrays.asList(POISON, STEEL);
            default:        throw new IllegalStateException();
        }
    }

    /**
     * Returns a list of Types that this Type receives 0x damage from
     *
     * @return a list of Types that this Type receives 0x damage from
     * @throws IllegalStateException if this Type is not handled by this method
     */
    public List<Type> immunities() throws IllegalStateException {
        switch (this) {
            case NORMAL:    return Arrays.asList(GHOST);
            case FIRE:      return Arrays.asList();
            case WATER:     return Arrays.asList();
            case GRASS:     return Arrays.asList();
            case ELECTRIC:  return Arrays.asList();
            case ICE:       return Arrays.asList();
            case FIGHTING:  return Arrays.asList();
            case POISON:    return Arrays.asList();
            case GROUND:    return Arrays.asList(ELECTRIC);
            case FLYING:    return Arrays.asList(GROUND);
            case PSYCHIC:   return Arrays.asList();
            case BUG:       return Arrays.asList();
            case ROCK:      return Arrays.asList();
            case GHOST:     return Arrays.asList(NORMAL, FIGHTING);
            case DRAGON:    return Arrays.asList();
            case DARK:      return Arrays.asList(PSYCHIC);
            case STEEL:     return Arrays.asList(POISON);
            case FAIRY:     return Arrays.asList(DRAGON);
            default:        throw new IllegalStateException();
        }
    }

    /**
     * Returns a list of Types that receives 1x damage from this Type
     *
     * @return a list of Types that receives 1x damage from this Type
     */
    public List<Type> normalAgainst() {
        List<Type> types = new ArrayList<>(Arrays.asList(Type.values()));
        for (Type t : Type.values()) {
            if (t.immunities().contains(this) | t.weaknesses().contains(this) | t.strengths().contains(this)) {
                types.remove(t);
            }
        }
        return types;
    }

    /**
     * Returns a list of Types that receives 2x damage from this Type
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
     * Returns a list of Types that receives 0.5x damage from this Type
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
     * Returns a list of Types that receives 0x damage from this Type
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
     * Returns a Map of multipliers when this Type is attacked by each Type
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
     * Returns a Map of combined multipliers when a set of arbitrary number of Types are attacked by each Type
     *
     * @param types a list of Types of arbitrary size
     * @return a Map of combined multipliers when a set of arbitrary number of Types are attacked by each Type
     */
    public static Map<Type, Double> defensiveMultipliers(List<Type> types) {
        Map<Type, Double> multipliers = new LinkedHashMap<>();
        if (types.size() == 1) {
            return types.get(0).defensiveMultipliers();
        }
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
     * Returns a Map of multipliers when this Type attacks each Type
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
     * Returns a Map of combined multipliers when a set of arbitrary number of Types attacks each Type
     *
     * @param types a list of Types of arbitrary size
     * @return a Map of combined multipliers when a set of arbitrary number of Types attacks each Type
     */
    public static Map<Type, Double> offensiveMultipliers(List<Type> types) {
        Map<Type, Double> multipliers = new LinkedHashMap<>();
        if (types.size() == 1) {
            return types.get(0).offensiveMultipliers();
        }
        for (Type t : Type.values()) {
            Double multiplier = 1.0;
            for (int i = 0; i < types.size(); i++) {
                multiplier *= types.get(i).offensiveMultipliers().get(t);
            }
            multipliers.put(t, multiplier);
        }
        return multipliers;
    }

    /**
     * Parses a Type from a String
     *
     * @param str a String that has the name of a Type
     * @return a Type parsed from a String
     * @throws IllegalArgumentException if the String cannot be parsed to Type
     */
    public static Type fromString(String str) throws IllegalArgumentException {
        switch (str) {
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
     * Return a list of all Types parsed from a space separated String
     *
     * REQUIRES: str is space separated
     *
     * @param str a space separated String that contains the name of Types
     * @return a list of all Types parsed from a space separated String
     */
    public static List<Type> stringToTypes(String str) throws IllegalArgumentException {
        String[] strs = str.toLowerCase().split(" ");
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

    /**
     * Generates a Map that's an analysis of a list of types
     *
     * @return a Map that's an analysis of a list of types
     */
    public static Map<Type, Double> analyzeOffense(List<Type> types) {
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
}
