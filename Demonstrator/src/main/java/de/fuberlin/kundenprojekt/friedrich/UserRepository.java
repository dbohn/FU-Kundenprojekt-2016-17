package de.fuberlin.kundenprojekt.friedrich;

import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.storage.Database;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author davidbohn
 */
public class UserRepository {

    public static List<User> getUserList() {
        Session session = Database.getSession();

        TypedQuery<User> users = session.createQuery("from User", User.class);
        List<User> userList = users.getResultList();

        session.close();

        return userList;
    }

    public static void storeUser(User user) {

        Session session = Database.getSession();

        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();

        session.close();
    }
}
