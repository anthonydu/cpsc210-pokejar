package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestSubjects;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests model.TeamList class.
 *
 * @author Anthony Du
 */
public class TeamListTest extends TestSubjects {
    private TeamList teams;

    @BeforeEach
    public void setup() {
        teams = new TeamList();
        teams.add(new Team("myTeam", Arrays.asList(tinkaton, rotom, cetitan)));
        teams.add(new Team("myTeam", Arrays.asList(rotom, tinkaton)));
    }

    @Test
    public void testToString() {
        assertEquals(
                "0 myTeam          Tinkaton        Rotom           Cetitan\n" +
                "1 myTeam          Rotom           Tinkaton",
                teams.toString());
    }
}
