package persistence;

/**
 * An exception that is thrown when a Jar's state is invalid.
 *
 * @auther Anthony Du
 */
public class InvalidJarException extends Exception {
    /**
     * Constructs an InvalidJarException with message
     *
     * @param message the message for this Exception
     */
    public InvalidJarException(String message) {
        super(message);
    }
}
