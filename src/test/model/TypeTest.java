package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    /** Tests that no type have conflicting strength or weakness or immunity */
    @Test
    public void testGetterConflicts() {
        for (Type t : Type.values()) {
            assertFalse(hasConflict(t));
        }
    }

    private boolean hasConflict(Type type) {
        for (Type s : type.getStrengths()) {
            for (Type w : type.getWeaknesses()) {
                for (Type i : type.getImmunities()) {
                    if (s == w || s == i || w == i) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}