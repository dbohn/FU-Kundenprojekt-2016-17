package de.fuberlin.kundenprojekt.friedrich.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Team Friedrich
 */
public class ServletLifecycleListener implements ServletContextListener {

    ServletContext sc;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sc = sce.getServletContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
