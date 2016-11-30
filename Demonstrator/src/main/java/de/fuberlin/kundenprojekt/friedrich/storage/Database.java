package de.fuberlin.kundenprojekt.friedrich.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.logging.Level;

/**
 * @author davidbohn
 */
public class Database {
    public static SessionFactory sessionFactory;

    public static Session getSession() {
        if (sessionFactory == null) {
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

            sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();

            //mainSession = sessionFactory.openSession();
        }

        return sessionFactory.openSession();
    }
}
