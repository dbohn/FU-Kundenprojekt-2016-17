package de.fuberlin.kundenprojekt.friedrich.roles;

import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.BaseServlet;
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
 * Endpoint to create a new role.
 *
 * @author Team Friedrich
 */
@WebServlet("roles")
public class RolesEndpoint extends BaseServlet {

    @Inject
    RoleRepository roleRepository;

    @Inject
    UserRepository userRepository;

    /**
     * Save a new role in the database.
     *
     * @param req The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException If writing or reading the response/request fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        List<String> selectedUserIds = getParameterList("users", req);

        List<User> selectedUsers = userRepository.getUserByIdList(selectedUserIds);

        HashSet<User> users = new HashSet<>(selectedUsers);

        Role role = new Role(name);

        role.setUsers(users);

        roleRepository.storeRole(role);

        req.setAttribute("status", "Project successfully added!");

        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
