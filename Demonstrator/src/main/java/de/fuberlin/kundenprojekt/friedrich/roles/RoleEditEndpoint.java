package de.fuberlin.kundenprojekt.friedrich.roles;

import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.endpoints.BaseServlet;
import de.fuberlin.kundenprojekt.friedrich.models.Role;
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
 * Endpoint to edit a role.
 *
 * @author Team Friedrich
 */
@WebServlet("/role/edit")
public class RoleEditEndpoint extends BaseServlet {

    @Inject
    RoleRepository roleRepository;

    @Inject
    UserRepository userRepository;

    /**
     * Display the edit form for a role
     *
     * @param req The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException If writing or reading the response/request fails
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectId = req.getParameter("role_id");

        Role role = roleRepository.getRoleById(projectId);

        req.setAttribute("role", role);

        req.getRequestDispatcher("../WEB-INF/editRole.jsp").forward(req, resp);
    }

    /**
     * Save an updated role in the database.
     *
     * @param req The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException If writing or reading the response/request fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roleId = req.getParameter("role_id");

        List<String> selectedUserIds = getParameterList("users", req);
        List<User> selectedUsers = userRepository.getUserByIdList(selectedUserIds);

        Role role = roleRepository.getRoleById(roleId);

        role.setName(req.getParameter("name"));

        HashSet<User> users = new HashSet<>(selectedUsers);

        role.setUsers(users);

        roleRepository.storeRole(role);

        req.setAttribute("status", "Role successfully edited!");

        req.setAttribute("role", role);

        req.getRequestDispatcher("../WEB-INF/editRole.jsp").forward(req, resp);
    }
}
