package de.fuberlin.kundenprojekt.friedrich.social;

/**
 * @author Team Friedrich
 */
public class Configuration {
    private static String host = "http://nginx";
    // TODO: This has to be configurable of some kind as it is different for every user
    private static String bcsToken = "IVa9aWe6rFneYtEpJEXiVTS4gKmBuoXvPLVxpnRaE2xubLzeV4Pbn8QK284v";

    public static String getHost() {
        return host;
    }

    public static String getBcsToken() {
        return bcsToken;
    }
}
