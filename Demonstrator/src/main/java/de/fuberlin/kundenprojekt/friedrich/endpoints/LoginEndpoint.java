package de.fuberlin.kundenprojekt.friedrich.endpoints;

import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.exceptions.AuthenticationException;
import de.fuberlin.kundenprojekt.friedrich.models.User;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author davidbohn
 */
@WebServlet("/login")
public class LoginEndpoint extends HttpServlet {

    @Inject
    private UserRepository userRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("./login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // TODO: Add validation

        try {
            User user = userRepository.validateCredentials(email, password);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath());
        } catch (AuthenticationException e) {
            req.setAttribute("error", "failed");
            req.getRequestDispatcher("./login.jsp").forward(req, resp);
        }
    }
}
