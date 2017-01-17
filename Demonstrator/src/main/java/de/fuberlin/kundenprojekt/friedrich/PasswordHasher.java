package de.fuberlin.kundenprojekt.friedrich;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Team Friedrich
 */
public class PasswordHasher {

    /**
     * Encrypt the provided password using BCrypt
     * @param password The raw password
     * @return The encrypted hash
     */
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Verify a provided password against a hash from the database
     * @param password The entered password
     * @param hash The saved hash
     * @return true if the password matches the hash, false otherwise.
     */
    public boolean verify(String password, String hash) {
        try {
            return BCrypt.checkpw(password, hash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
