package de.fuberlin.kundenprojekt.friedrich;

import de.fuberlin.kundenprojekt.friedrich.models.User;

import java.util.Set;

/**
 * @author Team Friedrich
 */
public class CustomTagLibrary {

    public static boolean contains(Set set, Object o) {
        if (o instanceof User) {
            return set.stream().anyMatch(user -> ((User) user).getId().equals(((User) o).getId()));
        }
        return set.contains(o);
    }
}
