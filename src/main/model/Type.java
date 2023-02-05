package model;

import java.util.*;

public enum Type {
    NORMAL, FIRE, WATER, GRASS, ELECTRIC, ICE, FIGHTING, POISON, GROUND,
    FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY;

    public List<Type> strengths() throws IllegalCallerException {
        switch (this) {
            case NORMAL:    return List.of();
            case FIRE:      return List.of(FIRE, GRASS, ICE, BUG, STEEL, FAIRY);
            case WATER:     return List.of(FIRE, WATER, ICE, STEEL);
            case GRASS:     return List.of(WATER, GRASS, ELECTRIC, GROUND);
            case ELECTRIC:  return List.of(ELECTRIC, FLYING, STEEL);
            case ICE:       return List.of(ICE);
            case FIGHTING:  return List.of(BUG, ROCK, DARK);
            case POISON:    return List.of(GRASS, FIGHTING, POISON, BUG, FAIRY);
            case GROUND:    return List.of(POISON, ROCK);
            case FLYING:    return List.of(GRASS, FIGHTING, BUG);
            case PSYCHIC:   return List.of(FIGHTING, PSYCHIC);
            case BUG:       return List.of(GRASS, FIGHTING, GROUND);
            case ROCK:      return List.of(NORMAL, FIRE, POISON, FLYING);
            case GHOST:     return List.of(POISON, BUG);
            case DRAGON:    return List.of(FIRE, WATER, GRASS, ELECTRIC);
            case DARK:      return List.of(GHOST, DARK);
            case STEEL:     return List.of(NORMAL, GRASS, ICE, FLYING, PSYCHIC, BUG, ROCK, DRAGON, STEEL, FAIRY);
            case FAIRY:     return List.of(FIGHTING, BUG, DARK);
            default:        throw new IllegalCallerException();
        }
    }

    public List<Type> weaknesses() throws IllegalCallerException {
        switch (this) {
            case NORMAL:    return List.of(FIGHTING);
            case FIRE:      return List.of(WATER, GROUND, ROCK);
            case WATER:     return List.of(GRASS, ELECTRIC);
            case GRASS:     return List.of(FIRE, ICE, POISON, FLYING, BUG);
            case ELECTRIC:  return List.of(GROUND);
            case ICE:       return List.of(FIRE, FIGHTING, ROCK, STEEL);
            case FIGHTING:  return List.of(FLYING, PSYCHIC, FAIRY);
            case POISON:    return List.of(GROUND, PSYCHIC);
            case GROUND:    return List.of(WATER, GRASS, ICE);
            case FLYING:    return List.of(ELECTRIC, ICE, ROCK);
            case PSYCHIC:   return List.of(BUG, GHOST, DARK);
            case BUG:       return List.of(FIRE, FLYING, ROCK);
            case ROCK:      return List.of(WATER, GRASS, FIGHTING, GROUND);
            case GHOST:     return List.of(GHOST, DARK);
            case DRAGON:    return List.of(ICE, DRAGON, FAIRY);
            case DARK:      return List.of(FIGHTING, BUG, FAIRY);
            case STEEL:     return List.of(FIRE, FIGHTING, GROUND);
            case FAIRY:     return List.of(POISON, STEEL);
            default:        throw new IllegalCallerException();
        }
    }

    public List<Type> immunities() throws IllegalCallerException {
        switch (this) {
            case NORMAL:    return List.of(GHOST);
            case FIRE:      return List.of();
            case WATER:     return List.of();
            case GRASS:     return List.of();
            case ELECTRIC:  return List.of();
            case ICE:       return List.of();
            case FIGHTING:  return List.of();
            case POISON:    return List.of();
            case GROUND:    return List.of(ELECTRIC);
            case FLYING:    return List.of(GROUND);
            case PSYCHIC:   return List.of();
            case BUG:       return List.of();
            case ROCK:      return List.of();
            case GHOST:     return List.of(NORMAL, FIGHTING);
            case DRAGON:    return List.of();
            case DARK:      return List.of(PSYCHIC);
            case STEEL:     return List.of(POISON);
            case FAIRY:     return List.of(DRAGON);
            default:        throw new IllegalCallerException();
        }
    }

    public List<Type> normalAgainst() {
        List<Type> types = new ArrayList<>(Arrays.asList(Type.values()));
        for (Type t : Type.values()) {
            if (t.immunities().contains(this)) {
                types.remove(t);
            }
        }
        return types;
    }

    public List<Type> strongAgainst() {
        List<Type> types = new ArrayList<>();
        for (Type t : Type.values()) {
            if (t.weaknesses().contains(this)) {
                types.add(t);
            }
        }
        return types;
    }

    public List<Type> weakAgainst() {
        List<Type> types = new ArrayList<>();
        for (Type t : Type.values()) {
            if (t.strengths().contains(this)) {
                types.add(t);
            }
        }
        return types;
    }

    public List<Type> noEffectAgainst() {
        List<Type> types = new ArrayList<>();
        for (Type t : Type.values()) {
            if (t.immunities().contains(this)) {
                types.add(t);
            }
        }
        return types;
    }

    private static Type fromString(String str) throws IllegalArgumentException {
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
            default: throw new IllegalArgumentException();
        }
    }

    public Map<Type, Double> defensiveMultipliers() {
        Map<Type, Double> multipliers = new HashMap<>();
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

    public static Map<Type, Double> defensiveMultipliers(List<Type> types) {
        Map<Type, Double> multipliers = new HashMap<>();
        if (types.size() == 1) {
            return types.get(0).defensiveMultipliers();
        }
        for (Type t : Type.values()) {
            multipliers.put(t, types.get(0).defensiveMultipliers().get(t) * types.get(1).defensiveMultipliers().get(t));
        }
        return multipliers;
    }

    public Map<Type, Double> offensiveMultipliers() {
        Map<Type, Double> multipliers = new HashMap<>();
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

    public static Map<Type, Double> offensiveMultipliers(List<Type> types) {
        Map<Type, Double> multipliers = new HashMap<>();
        if (types.size() == 1) {
            return types.get(0).offensiveMultipliers();
        }
        for (Type t : Type.values()) {
            multipliers.put(t, types.get(0).offensiveMultipliers().get(t) * types.get(1).offensiveMultipliers().get(t));
        }
        return multipliers;
    }

    public static ArrayList<Type> stringToTypes(String str) throws IllegalArgumentException {
        String[] strs = str.toLowerCase().split(" ");
        if (strs.length == 0 || strs.length > 2) {
            throw new IllegalArgumentException("Zero or more than two types found!");
        }
        ArrayList<Type> types = new ArrayList<>();
        for (String s : strs) {
            types.add(Type.fromString(s));
        }
        return types;
    }

    public static Type stringToType(String str) throws IllegalArgumentException {
        String[] strs = str.toLowerCase().split(" ");
        if (strs.length != 1) {
            throw new IllegalArgumentException("Zero or more than one type found!");
        } else {
            return stringToTypes(str).get(0);
        }
    }
}
