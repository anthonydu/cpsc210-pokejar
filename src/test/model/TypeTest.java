package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import static model.Type.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests model.Type enum class
 *
 * @author Anthony Du
 */
public class TypeTest {

    /**
     * Tests that no Type have conflicting strength, weakness or immunity.
     */
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
        assertEquals(0.5, offensiveMultipliers(Arrays.asList(NORMAL)).get(STEEL));
        assertEquals(1.0, offensiveMultipliers(Arrays.asList(GRASS, BUG)).get(BUG));
        assertEquals(2.0, offensiveMultipliers(Arrays.asList(NORMAL, GHOST)).get(GHOST));
        assertEquals(0.5, offensiveMultipliers(Arrays.asList(ELECTRIC, POISON)).get(GROUND));
        assertEquals(0.0, offensiveMultipliers(Arrays.asList(FIGHTING, NORMAL)).get(GHOST));
    }

    @Test
    public void testFromSpaceSeparatedString() {
        for (Type t : values()) {
            assertEquals(t, fromString(t.toString()));
        }
        assertThrows(IllegalArgumentException.class, () -> fromString("spicy pepper"));
        assertThrows(IllegalArgumentException.class, () -> fromSpaceSeparatedString("dark grassy"));

        assertEquals(
                Arrays.asList(GROUND),
                fromSpaceSeparatedString("ground")
        );
        assertEquals(
                Arrays.asList(PSYCHIC, POISON),
                fromSpaceSeparatedString("pSyChIc PoISoN")
        );
        assertEquals(
                Arrays.asList(FIRE, GRASS, FAIRY),
                fromSpaceSeparatedString("fire grass fairy")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> fromSpaceSeparatedString("steel normal steel")
        );
    }

    /**
     * Checks if a Type has conflicting strengths, weaknesses, or immunities
     *
     * @param type the Type to be checked
     * @return true if conflict is found and false otherwise
     */
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