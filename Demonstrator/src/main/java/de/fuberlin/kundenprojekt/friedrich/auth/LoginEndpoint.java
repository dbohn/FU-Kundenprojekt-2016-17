package de.fuberlin.kundenprojekt.friedrich.auth;

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
 * Endpoint to handle login requests.
 *
 * @author Team Friedrich
 */
@WebServlet("/login")
public class LoginEndpoint extends HttpServlet {

    @Inject
    private UserRepository userRepository;

    /**
     * Display the login form.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }

    /**
     * Handle an incoming login request.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = userRepository.validateCredentials(email, password);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath());
        } catch (AuthenticationException e) {
            req.setAttribute("error", "failed");
            req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
        }
    }
}
