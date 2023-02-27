package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests model.Jar class.
 *
 * @author Anthony Du
 */
public class JarTest {
    private Jar jar;

    @BeforeEach
    public void setup() {
        jar = new Jar();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, jar.getBox().size());
        assertEquals(0, jar.getTeams().size());
    }
}
