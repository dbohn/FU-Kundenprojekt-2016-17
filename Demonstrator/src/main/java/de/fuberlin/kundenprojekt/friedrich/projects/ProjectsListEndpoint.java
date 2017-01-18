package de.fuberlin.kundenprojekt.friedrich.projects;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.endpoints.BaseServlet;
import de.fuberlin.kundenprojekt.friedrich.models.Project;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.Configuration;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * @author Team Friedrich
 */
@WebServlet("/projects")
public class ProjectsListEndpoint extends BaseServlet {

    @Inject
    ProjectsRepository projectsRepository;

    @Inject
    UserRepository userRepository;

    /**
     * List all projects and provide form to create a new one.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/projects.jsp").forward(req, resp);
    }

    /**
     * Create a new project with the provided request data.
     * <p>
     * The currently authenticated user will be set as the
     * creator of the space, that is linked with this
     * project.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = new Project(req.getParameter("name"), req.getParameter("description"));

        User user = user(req);

        HumHubSpaces humHubSpaces = new HumHubSpaces(Configuration.getHost(), Configuration.getBcsToken());

        List<String> selectedUserIds = getParameterList("users", req);

        List<User> selectedUsers = userRepository.getUserByIdList(selectedUserIds);

        HashSet<User> users = new HashSet<>(selectedUsers);

        project.setUsers(users);

        projectsRepository.storeProject(project);

        try {
            if (humHubSpaces.create(project.getId(), project.getName(), project.getDescription(), user)) {
                req.setAttribute("status", "Project successfully added!");
            } else {
                req.setAttribute("error", "Unable to sync to HumHub!");
            }
        } catch (UnirestException e) {
            req.setAttribute("error", "Unable to sync to HumHub!");
            e.printStackTrace();
        }

        req.getRequestDispatcher("WEB-INF/projects.jsp").forward(req, resp);
    }
}
