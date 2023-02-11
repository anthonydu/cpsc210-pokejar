package model;

import org.junit.jupiter.api.Test;
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
        for (Type t : Type.values()) {
            assertFalse(hasConflict(t));
        }
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