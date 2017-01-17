package de.fuberlin.kundenprojekt.friedrich;

import de.fuberlin.kundenprojekt.friedrich.exceptions.AuthenticationException;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.models.Userinfo;
import de.fuberlin.kundenprojekt.friedrich.storage.Database;
import org.hibernate.Session;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author Team Friedrich
 */
@Named
@RequestScoped
public class UserRepository {

    @Inject
    PasswordHasher passwordHasher;

    public UserRepository() {
    }

    /**
     * Retrieve a list of all users.
     *
     * @return The list of users
     */
    @Produces
    @Named
    public List<User> getUserList() {
        Session session = Database.getSession();

        TypedQuery<User> users = session.createQuery("from User order by full_name", User.class);
        List<User> userList = users.getResultList();

        session.close();

        return userList;
    }

    /**
     * Find a user by it's id in the demonstrator
     * @param id The user id (a UUID)
     * @return The user or null, if none found
     */
    public User getUserById(String id) {
        Session session = Database.getSession();

        TypedQuery<User> userQuery = session.createQuery("from User where id=:user", User.class)
                .setParameter("user", id);

        User user = userQuery.getSingleResult();

        session.close();

        return user;
    }

    /**
     * Fetch multiple users by their ids
     * @param ids The ids of the users
     * @return The list of found users
     */
    public List<User> getUserByIdList(List<String> ids) {
        Session session = Database.getSession();

        TypedQuery<User> userQuery = session.createQuery("from User where id in (:ids)", User.class)
                .setParameterList("ids", ids);
        List<User> userList = userQuery.getResultList();

        session.close();

        return userList;
    }

    /**
     * Validate the given credentials and resolve them into a user
     * @param email The login e-mail
     * @param password The login password
     * @return The successfully logged in user.
     * @throws AuthenticationException If there is no user with the given credentials
     */
    public User validateCredentials(String email, String password) throws AuthenticationException {
        Session session = Database.getSession();
        TypedQuery<User> userQuery = session.createQuery("from User where email=:email", User.class)
                .setParameter("email", email);

        User user;
        try {
            user = userQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new AuthenticationException();
        }

        if (user == null) {
            throw new AuthenticationException();
        }

        if (!passwordHasher.verify(password, user.getPassword())) {
            // Rehash current demo password
            // TODO: Drop this anytime soon
            if (user.getPassword().equals("password")) {
                user.password = passwordHasher.hash(password);
                UserRepository.storeUser(user);
            } else {
                throw new AuthenticationException();
            }
        }

        session.close();

        return user;
    }

    /**
     * Save the given user in the database.
     *
     * @param user The user, that should be saved
     */
    public static void storeUser(User user) {

        if (user.getUserinfo() == null) {
            Userinfo userinfo = new Userinfo(LocalDateTime.now(), LocalDateTime.now(), null);
            user.addUserinfo(userinfo);
        }

        Session session = Database.getSession();

        session.beginTransaction();

        session.saveOrUpdate(user);

        session.getTransaction().commit();

        session.close();
    }

    /**
     * Remove a user from the Postgres database
     * @param user The user to delete
     */
    public void deleteUser(User user) {
        Session session = Database.getSession();

        session.beginTransaction();

        session.delete(user);

        session.getTransaction().commit();

        session.close();
    }
}
