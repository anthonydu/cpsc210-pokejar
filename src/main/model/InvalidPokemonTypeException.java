package model;

/**
 * An exception thrown by the Type enum.
 *
 * @auther Anthony Du
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
