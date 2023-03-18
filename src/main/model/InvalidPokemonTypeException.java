package model;

/**
 * An exception thrown when a string cannot be converted to a Type.
 *
 * @author Anthony Du
 */
public class InvalidPokemonTypeException extends Exception {
    /**
     * Constructs a InvalidPokemonTypeException with message
     *
     * @param message the message for this Exception
     */
    public InvalidPokemonTypeException(String message) {
        super(message);
    }
}
