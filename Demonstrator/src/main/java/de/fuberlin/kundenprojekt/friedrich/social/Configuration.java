package de.fuberlin.kundenprojekt.friedrich.social;

/**
 * This class holds some configuration values.
 *
 * @author Team Friedrich
 */
public class Configuration {
    /**
     * The host, where HumHub lives.
     * Attention: In a Docker environment this has to be the container internal URL!
     */
    private static String host = "http://nginx";

    /**
     * The BCS token is used to authenticate requests to HumHub.
     */
    private static String bcsToken = "IVa9aWe6rFneYtEpJEXiVTS4gKmBuoXvPLVxpnRaE2xubLzeV4Pbn8QK284v";

    /**
     * Get the set host.
     *
     * @return the host
     */
    public static String getHost() {
        return host;
    }

    /**
     * Get the BCS Token.
     *
     * @return the token
     */
    public static String getBcsToken() {
        return bcsToken;
    }
}
