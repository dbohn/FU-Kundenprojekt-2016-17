package de.fuberlin.kundenprojekt.friedrich;

import de.fuberlin.kundenprojekt.friedrich.models.User;

import java.util.List;
import java.util.Set;

/**
 * This class provides some handy helpers for the Java EL.
 *
 * @author Team Friedrich
 */
public class CustomTagLibrary {

    /**
     * Check if the given set contains the Object o.
     * <p>
     * We do need some special handling for users as the equals method
     * is somehow ignored.</p>
     *
     * @param set The set in which the item might be (haystack)
     * @param o   The needle
     * @return true if the element is in the set, false otherwise
     */
    public static boolean contains(Set set, Object o) {
        if (o instanceof User) {
            return set.stream().anyMatch(user -> ((User) user).getId().equals(((User) o).getId()));
        }
        return set.contains(o);
    }

    /**
     * Check if the given list contains the Object o.
     * <p>
     * We do need some special handling for users as the equals method
     * is somehow ignored.</p>
     *
     * @param set The list in which the item might be (haystack)
     * @param o   The needle
     * @return true if the element is in the set, false otherwise
     */
    public static boolean contains(List set, Object o) {
        if (o instanceof User) {
            return set.stream().anyMatch(user -> ((User) user).getId().equals(((User) o).getId()));
        }
        return set.contains(o);
    }

    /**
     * Handy helper to quickly convert a list of strings to a string.
     *
     * @param list The list of strings to join
     * @return The joined string
     */
    public static String listToString(List<String> list) {
        return String.join(",", list);
    }
}
