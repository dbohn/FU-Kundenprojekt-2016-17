package de.fuberlin.kundenprojekt.friedrich;

import de.fuberlin.kundenprojekt.friedrich.exceptions.AuthenticationException;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.storage.Database;
import org.hibernate.Session;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Team Friedrich
 */
@Named
@RequestScoped
public class UserRepository {

    public UserRepository() {
    }

    @Produces
    @Named
    public List<User> getUserList() {
        Session session = Database.getSession();

        TypedQuery<User> users = session.createQuery("from User", User.class);
        List<User> userList = users.getResultList();

        session.close();

        return userList;
    }

    public User getUserById(String id) {
        Session session = Database.getSession();

        TypedQuery<User> userQuery = session.createQuery("from User where id=:user", User.class)
                .setParameter("user", id);

        User user = userQuery.getSingleResult();

        session.close();

        return user;
    }

    public User validateCredentials(String email, String password) throws AuthenticationException {
        Session session = Database.getSession();
        TypedQuery<User> userQuery = session.createQuery("from User where email=:email and password=:password", User.class)
                .setParameter("email", email)
                .setParameter("password", password);

        User user;
        try {
            user = userQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new AuthenticationException();
        }

        if (user == null) {
            throw new AuthenticationException();
        }

        session.close();

        return user;
    }

    public static void storeUser(User user) {

        Session session = Database.getSession();

        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();

        session.close();
    }
}
