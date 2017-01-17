package de.fuberlin.kundenprojekt.friedrich.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;

/**
 * Factory for a Hibernate session.
 *
 * @author Team Friedrich
 */
public class Database {
    public static SessionFactory sessionFactory;

    /**
     * Get a new Hibernate session.
     *
     * @return the Hibernate session
     */
    public static Session getSession() {
        if (sessionFactory == null) {
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

            sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
        }

        return sessionFactory.openSession();
    }
}
