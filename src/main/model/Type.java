package model;

public enum Type {
    NORMAL, FIRE, WATER, GRASS, ELECTRIC, ICE, FIGHTING, POISON, GROUND,
    FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY;

    public Type[] getStrengths() {
        switch (this) {
            case NORMAL:    return new Type[]{};
            case FIRE:      return new Type[]{FIRE, GRASS, ICE, BUG, STEEL, FAIRY};
            case WATER:     return new Type[]{FIRE, WATER, ICE, STEEL};
            case GRASS:     return new Type[]{WATER, GRASS, ELECTRIC, GROUND};
            case ELECTRIC:  return new Type[]{ELECTRIC, FLYING, STEEL};
            case ICE:       return new Type[]{ICE};
            case FIGHTING:  return new Type[]{BUG, ROCK, DARK};
            case POISON:    return new Type[]{GRASS, FIGHTING, POISON, BUG, FAIRY};
            case GROUND:    return new Type[]{POISON, ROCK};
            case FLYING:    return new Type[]{GRASS, FIGHTING, BUG};
            case PSYCHIC:   return new Type[]{FIGHTING, PSYCHIC};
            case BUG:       return new Type[]{GRASS, FIGHTING, GROUND};
            case ROCK:      return new Type[]{NORMAL, FIRE, POISON, FLYING};
            case GHOST:     return new Type[]{POISON, BUG};
            case DRAGON:    return new Type[]{FIRE, WATER, GRASS, ELECTRIC};
            case DARK:      return new Type[]{GHOST, DARK};
            case STEEL:     return new Type[]{NORMAL, GRASS, ICE, FLYING, PSYCHIC, BUG, ROCK, DRAGON, STEEL, FAIRY};
            case FAIRY:     return new Type[]{FIGHTING, BUG, DARK};
            default:        throw new IllegalCallerException();
        }
    }

    public Type[] getWeaknesses() {
        switch (this) {
            case NORMAL:    return new Type[]{FIGHTING};
            case FIRE:      return new Type[]{WATER, GROUND, ROCK};
            case WATER:     return new Type[]{GRASS, ELECTRIC};
            case GRASS:     return new Type[]{FIRE, ICE, POISON, FLYING, BUG};
            case ELECTRIC:  return new Type[]{GROUND};
            case ICE:       return new Type[]{FIRE, FIGHTING, ROCK, STEEL};
            case FIGHTING:  return new Type[]{FLYING, PSYCHIC, FAIRY};
            case POISON:    return new Type[]{GROUND, PSYCHIC};
            case GROUND:    return new Type[]{WATER, GRASS, ICE};
            case FLYING:    return new Type[]{ELECTRIC, ICE, ROCK};
            case PSYCHIC:   return new Type[]{BUG, GHOST, DARK};
            case BUG:       return new Type[]{FIRE, FLYING, ROCK};
            case ROCK:      return new Type[]{WATER, GRASS, FIGHTING, GROUND};
            case GHOST:     return new Type[]{GHOST, DARK};
            case DRAGON:    return new Type[]{ICE, DRAGON, FAIRY};
            case DARK:      return new Type[]{FIGHTING, BUG, FAIRY};
            case STEEL:     return new Type[]{FIRE, FIGHTING, GROUND};
            case FAIRY:     return new Type[]{POISON, STEEL};
            default:        throw new IllegalCallerException();
        }
    }

    public Type[] getImmunities() {
        switch (this) {
            case FIRE:
            case WATER:
            case GRASS:
            case ELECTRIC:
            case ICE:
            case FIGHTING:
            case POISON:
            case PSYCHIC:
            case BUG:
            case ROCK:
            case DRAGON:
                return new Type[]{};
            case NORMAL:    return new Type[]{GHOST};
            case GROUND:    return new Type[]{ELECTRIC};
            case FLYING:    return new Type[]{GROUND};
            case GHOST:     return new Type[]{NORMAL, FIGHTING};
            case DARK:      return new Type[]{PSYCHIC};
            case STEEL:     return new Type[]{POISON};
            case FAIRY:     return new Type[]{DRAGON};
            default:        throw new IllegalCallerException();
        }
    }
}
