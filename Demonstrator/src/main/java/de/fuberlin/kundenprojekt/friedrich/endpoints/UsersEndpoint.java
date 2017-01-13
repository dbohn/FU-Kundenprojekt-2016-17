package de.fuberlin.kundenprojekt.friedrich.endpoints;

import de.fuberlin.kundenprojekt.friedrich.PasswordHasher;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.models.Userinfo;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * @author Team Friedrich
 */
@WebServlet("/users")
public class UsersEndpoint extends HttpServlet {

    @Inject
    PasswordHasher passwordHasher;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String first_name = req.getParameter("first_name");
        String last_name = req.getParameter("last_name");
        String password = passwordHasher.hash(req.getParameter("password").trim());
        String phone = req.getParameter("phone");

        String fullName =  last_name + ", " + first_name;

        User user = new User(username, fullName, email, password, phone);

        UserRepository.storeUser(user);

        req.setAttribute("status", "User successfully added!");

        req.getRequestDispatcher("WEB-INF/users.jsp").forward(req, resp);
    }
}
