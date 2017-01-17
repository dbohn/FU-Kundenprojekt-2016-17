package de.fuberlin.kundenprojekt.friedrich.endpoints;

import de.fuberlin.kundenprojekt.friedrich.PasswordHasher;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.models.User;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Endpoint to handle user listing and creation.
 *
 * @author Team Friedrich
 */
@WebServlet("/users")
public class UsersEndpoint extends HttpServlet {

    @Inject
    PasswordHasher passwordHasher;

    /**
     * Display a list of all users and show forms for user creation and import
     *
     * @param request  The incoming request
     * @param response The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("WEB-INF/users.jsp").forward(request, response);
    }

    /**
     * Store a posted user
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String first_name = req.getParameter("first_name");
        String last_name = req.getParameter("last_name");
        String password = passwordHasher.hash(req.getParameter("password").trim());
        String phone = req.getParameter("phone");

        String fullName = last_name + ", " + first_name;

        User user = new User(username, fullName, email, password, phone);

        UserRepository.storeUser(user);

        req.setAttribute("status", "User successfully added!");

        req.getRequestDispatcher("WEB-INF/users.jsp").forward(req, resp);
    }
}
