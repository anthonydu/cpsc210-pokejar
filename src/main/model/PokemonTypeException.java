package model;

/**
 * An exception thrown by the Type enum.
 *
 * @auther Anthony Du
 */
public class PokemonTypeException extends Exception {
    /**
     * Constructs a PokemonTypeException with message
     *
     * @param message the message for this Exception
     */
    public PokemonTypeException(String message) {
        super(message);
    }
}
