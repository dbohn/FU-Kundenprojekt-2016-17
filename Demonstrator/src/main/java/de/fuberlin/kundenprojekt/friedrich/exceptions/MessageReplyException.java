package de.fuberlin.kundenprojekt.friedrich.exceptions;

/**
 * This exception is thrown, if a chat message could not be sent.
 *
 * @author Team Friedrich
 */
public class MessageReplyException extends Exception {
    public MessageReplyException(String message) {
        super(message);
    }
}
