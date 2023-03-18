package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static model.Type.*;
import static model.Type.fromSafeString;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests model.Type enum class.
 *
 * @author Anthony Du
 */
public class TypeTest {

    @Test
    public void testFromSafeString() {
        for (Type t : values()) {
            assertEquals(t, fromSafeString(t.toString()));
        }
        assertThrows(IllegalArgumentException.class, () -> fromSafeString("String not safe"));
    }

    @Test
    public void testConflicts() {
        for (Type t : values()) {
            assertFalse(hasConflict(t));
        }
    }

    @Test
    public void testAgainst() {
        assertEquals(Arrays.asList(FIRE, WATER, GRASS, ELECTRIC, FIGHTING, GROUND, DRAGON), FIGHTING.normalAgainst());
        assertEquals(Arrays.asList(FIRE, GROUND, ROCK), WATER.strongAgainst());
        assertEquals(Arrays.asList(GRASS, PSYCHIC, DARK), BUG.strongAgainst());
        assertEquals(Arrays.asList(ELECTRIC, ROCK, STEEL), FLYING.weakAgainst());
        assertEquals(Arrays.asList(FIRE, POISON, STEEL), FAIRY.weakAgainst());
        assertEquals(Arrays.asList(NORMAL), GHOST.noEffectAgainst());
        assertEquals(Arrays.asList(GROUND), ELECTRIC.noEffectAgainst());
    }

    @Test
    public void testDefensiveMultipliers() {
        assertEquals(1.0, offensiveMultipliers(Arrays.asList()).get(STEEL));
        assertEquals(2.0, defensiveMultipliers(Arrays.asList(PSYCHIC)).get(BUG));
        assertEquals(1.0, defensiveMultipliers(Arrays.asList(NORMAL, FAIRY)).get(FIGHTING));
        assertEquals(2.0, defensiveMultipliers(Arrays.asList(WATER, DARK)).get(GRASS));
        assertEquals(4.0, defensiveMultipliers(Arrays.asList(GRASS, DRAGON)).get(ICE));
        assertEquals(0.5, defensiveMultipliers(Arrays.asList(ELECTRIC, GHOST)).get(POISON));
        assertEquals(0.25, defensiveMultipliers(Arrays.asList(STEEL, ROCK)).get(FLYING));
        assertEquals(0.0, defensiveMultipliers(Arrays.asList(FIRE, FLYING)).get(GROUND));
    }

    @Test
    public void testOffensiveMultipliers() {
        assertEquals(1.0, offensiveMultipliers(Arrays.asList()).get(STEEL));
        assertEquals(0.5, offensiveMultipliers(Arrays.asList(NORMAL)).get(STEEL));
        assertEquals(1.0, offensiveMultipliers(Arrays.asList(GRASS, BUG)).get(BUG));
        assertEquals(2.0, offensiveMultipliers(Arrays.asList(NORMAL, GHOST)).get(GHOST));
        assertEquals(0.5, offensiveMultipliers(Arrays.asList(ELECTRIC, POISON)).get(GROUND));
        assertEquals(0.0, offensiveMultipliers(Arrays.asList(FIGHTING, NORMAL)).get(GHOST));
    }

    @Test
    public void testFromListOfStrings() throws Exception {
        for (Type t : values()) {
            assertEquals(t, fromString(t.toString()));
        }
        assertThrows(InvalidPokemonTypeException.class, () -> fromString("spicy pepper"));
        assertThrows(InvalidPokemonTypeException.class, () -> fromListOfStrings(Arrays.asList("dark", "grassy")));

        assertEquals(
                Arrays.asList(GROUND),
                fromListOfStrings(Arrays.asList("ground"))
        );
        assertEquals(
                Arrays.asList(PSYCHIC, POISON),
                fromListOfStrings(Arrays.asList("pSyChIc", "PoISoN"))
        );
        assertEquals(
                Arrays.asList(FIRE, GRASS, FAIRY),
                fromListOfStrings(Arrays.asList("fire", "grass", "fairy"))
        );
        assertThrows(
                InvalidPokemonTypeException.class,
                () -> fromListOfStrings(Arrays.asList("steel", "normal", "steel"))
        );
    }

    private boolean hasConflict(Type type) {
        for (Type s : type.strengths()) {
            for (Type w : type.weaknesses()) {
                for (Type i : type.immunities()) {
                    if (s == w || s == i || w == i) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}