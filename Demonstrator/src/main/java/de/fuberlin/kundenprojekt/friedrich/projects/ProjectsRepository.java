package de.fuberlin.kundenprojekt.friedrich.projects;

import de.fuberlin.kundenprojekt.friedrich.models.Project;
import de.fuberlin.kundenprojekt.friedrich.storage.Database;
import org.hibernate.Session;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Team Friedrich
 */
public class ProjectsRepository {

    /**
     * Retrieve a list of all projects.
     *
     * @return The project list
     */
    @Produces
    @Named
    public List<Project> getProjectsList() {
        Session session = Database.getSession();

        TypedQuery<Project> projects = session.createQuery("from Project", Project.class);
        List<Project> projectList = projects.getResultList();

        session.close();

        return projectList;
    }

    /**
     * Get a project by the project id
     *
     * @param id the project id
     * @return the project, null if none found.
     */
    public Project getProjectById(String id) {
        Session session = Database.getSession();

        TypedQuery<Project> userQuery = session.createQuery("from Project where id=:project", Project.class)
                .setParameter("project", Long.parseLong(id));

        Project project = userQuery.getSingleResult();

        session.close();

        return project;
    }

    /**
     * Persist a project entity.
     *
     * @param project The project
     */
    public void storeProject(Project project) {
        Session session = Database.getSession();

        session.beginTransaction();

        session.saveOrUpdate(project);

        session.getTransaction().commit();

        session.close();
    }
}
