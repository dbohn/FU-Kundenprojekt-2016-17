package de.fuberlin.kundenprojekt.friedrich.endpoints;

import de.fuberlin.kundenprojekt.friedrich.PasswordHasher;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author davidbohn
 */

public class UsersEndpoint extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String username = request.getParameter("username");
        //String email = request.getParameter("email");

        resp(response, request.getMethod());

        //response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String first_name = req.getParameter("first_name");
        String last_name = req.getParameter("last_name");
        String password = PasswordHasher.createHash(req.getParameter("password"));
        String phone = req.getParameter("phone");

        String fullName = first_name + ' ' + last_name;

        User user = new User(username, fullName, email, password, phone);

        UserRepository.storeUser(user);

        //resp(resp, username);
        resp.sendRedirect("./users.jsp");
    }

    private void resp(HttpServletResponse resp, String msg) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<p>" + msg + "</p>");
        out.close();
    }
}
