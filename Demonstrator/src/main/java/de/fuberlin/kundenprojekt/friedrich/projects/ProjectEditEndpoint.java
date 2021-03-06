package de.fuberlin.kundenprojekt.friedrich.projects;

import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.BaseServlet;
import de.fuberlin.kundenprojekt.friedrich.models.Project;
import de.fuberlin.kundenprojekt.friedrich.models.User;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * Display an edit project form and save the edit.
 *
 * @author Team Friedrich
 */
@WebServlet("/projects/edit")
public class ProjectEditEndpoint extends BaseServlet {

    @Inject
    ProjectsRepository projectsRepository;

    @Inject
    UserRepository userRepository;

    /**
     * Render the form to edit a project.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectId = req.getParameter("project_id");

        Project project = projectsRepository.getProjectById(projectId);

        req.setAttribute("project", project);

        req.getRequestDispatcher("../WEB-INF/editProject.jsp").forward(req, resp);
    }

    /**
     * Save an updated project to the database.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectId = req.getParameter("project_id");

        List<String> selectedUserIds = getParameterList("users", req);
        List<User> selectedUsers = userRepository.getUserByIdList(selectedUserIds);

        Project project = projectsRepository.getProjectById(projectId);

        project.setName(req.getParameter("name"));
        project.setDescription(req.getParameter("description"));

        HashSet<User> users = new HashSet<>(selectedUsers);

        project.setUsers(users);

        projectsRepository.storeProject(project);

        req.setAttribute("status", "Project successfully added!");

        req.setAttribute("project", project);

        req.getRequestDispatcher("../WEB-INF/editProject.jsp").forward(req, resp);

    }
}
