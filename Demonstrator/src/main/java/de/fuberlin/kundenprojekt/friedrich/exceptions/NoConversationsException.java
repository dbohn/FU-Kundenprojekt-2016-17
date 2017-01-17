package de.fuberlin.kundenprojekt.friedrich.exceptions;

/**
 * This exception is thrown, if the conversations could not be retrieved.
 *
 * @author Team Friedrich
 */
public class NoConversationsException extends Exception {
    public NoConversationsException(String message) {
        super(message);
    }
}
