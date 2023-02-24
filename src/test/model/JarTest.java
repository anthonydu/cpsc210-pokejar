package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

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
