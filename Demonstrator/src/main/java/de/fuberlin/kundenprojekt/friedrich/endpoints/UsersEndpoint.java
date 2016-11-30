package de.fuberlin.kundenprojekt.friedrich.endpoints;

import com.mashape.unirest.http.JsonNode;
import de.fuberlin.kundenprojekt.friedrich.PasswordHasher;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubUserRepository;

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

        //DummyUser TODO: Connection to database, import user data
        User dummyUser = new User("hans","wurst, hans","han@gmx.de","999d","0000");
        dummyUser.id=5;

        //TODO: bcsSuperToken has to be adjusted
        HumHubUserRepository ur = new HumHubUserRepository("bDmLAezCwc1gSGuydWTTwi3LGglFK1lceXkegfoLbp07PZqZQrf6aQjx1ZzS","http://nginx");

        String status = ur.add(dummyUser);
        resp(response,status);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String first_name = req.getParameter("first_name");
        String last_name = req.getParameter("last_name");
        String password = PasswordHasher.createHash(req.getParameter("password"));
        String phone = req.getParameter("phone");

        String fullName =  last_name + ", " + first_name;

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
