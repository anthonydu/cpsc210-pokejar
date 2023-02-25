package util;

import model.Move;
import model.Pokemon;
import model.Type;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class containing premade Pokemon objects for testing use.
 *
 * @author Anthony Du
 */
public class TestSubjects {
    protected static final Pokemon tinkaton = new Pokemon(
            "Tinkaton",
            new ArrayList<>(Arrays.asList(Type.FAIRY, Type.STEEL)),
            new ArrayList<>(Arrays.asList(
                    new Move("Gigaton Hammer", Type.STEEL, false),
                    new Move("Play Rough", Type.FAIRY, false),
                    new Move("Swords Dance", Type.NORMAL, true),
                    new Move("Encore", Type.NORMAL, true)
            ))
    );
    protected static final Pokemon rotom = new Pokemon(
            "Rotom",
            new ArrayList<>(Arrays.asList(Type.ELECTRIC, Type.WATER)),
            new ArrayList<>(Arrays.asList(
                    new Move("Hydro Pump", Type.WATER, false),
                    new Move("Thunderbolt", Type.ELECTRIC, false),
                    new Move("Hex", Type.GHOST, false),
                    new Move("Nasty Plot", Type.DARK, true)

            ))
    );
    protected static final Pokemon cetitan = new Pokemon(
            "Cetitan",
            new ArrayList<>(Arrays.asList(Type.ICE)),
            new ArrayList<>(Arrays.asList(
                    new Move("Avalanche", Type.ICE, false),
                    new Move("Ice Shard", Type.ICE, false),
                    new Move("Earthquake", Type.GROUND, false),
                    new Move("Heavy Slam", Type.STEEL, false)

            ))
    );
}
