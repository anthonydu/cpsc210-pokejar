package persistence;

/**
 * An exception that is thrown when a Jar's state is invalid.
 *
 * @auther Anthony Du
 */
public class InvalidJarException extends Exception {
    public InvalidJarException(String message) {
        super(message);
    }
}
