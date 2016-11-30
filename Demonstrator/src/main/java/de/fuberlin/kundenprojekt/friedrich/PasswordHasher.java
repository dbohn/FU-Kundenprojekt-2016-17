package de.fuberlin.kundenprojekt.friedrich;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * @author davidbohn
 */
public class PasswordHasher {

    private static final int SALT_LENGTH = 24;
    private static final int ITERATIONS = 64000;
    private static final int HASH_BYTE_SIZE = 13;

    public static String createHash(String password) {
        byte[] salt = createSalt();

        byte[] passwordHash = hashPassword(password.toCharArray(), salt, ITERATIONS, HASH_BYTE_SIZE);

        return ITERATIONS+":"+passwordHash.length+":"+toBase64(salt)+":"+toBase64(passwordHash);
    }

    private static byte[] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int bytes) {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            SecretKey key = skf.generateSecret(spec);
            return key.getEncoded();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] fromBase64(String data) {
        return DatatypeConverter.parseBase64Binary(data);
    }

    private static String toBase64(byte[] data) {
        return DatatypeConverter.printBase64Binary(data);
    }
}
