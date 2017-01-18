package de.fuberlin.kundenprojekt.friedrich.roles;

import de.fuberlin.kundenprojekt.friedrich.endpoints.BaseServlet;
import de.fuberlin.kundenprojekt.friedrich.models.Role;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Team Friedrich
 */
@WebServlet("roles")
public class RolesEndpoint extends BaseServlet {

    @Inject
    RoleRepository roleRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        Role role = new Role(name);

        roleRepository.storeRole(role);

        req.setAttribute("status", "Project successfully added!");

        //req.getRequestDispatcher("WEB-INF/users.jsp").forward(req, resp);
        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
