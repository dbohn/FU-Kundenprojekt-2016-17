package de.fuberlin.kundenprojekt.friedrich;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Team Friedrich
 */
public class PasswordHasher {

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean verify(String password, String hash) {
        try {
            return BCrypt.checkpw(password, hash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
