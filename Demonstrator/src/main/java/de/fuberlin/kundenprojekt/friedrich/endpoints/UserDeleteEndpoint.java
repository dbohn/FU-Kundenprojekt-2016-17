package de.fuberlin.kundenprojekt.friedrich.endpoints;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.Configuration;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubApiUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Team Friedrich
 */
@WebServlet("user/delete")
public class UserDeleteEndpoint extends HttpServlet {

    @Inject
    private UserRepository userRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("user_id");

        User user = userRepository.getUserById(id);

        userRepository.deleteUser(user);

        try {
            // Tell HumHub to delete the user
            HumHubApiUtil.post(Configuration.getHost(), "/bcs/user/delete", Configuration.getBcsToken())
                .field("user_id", id)
                .asJson();
            req.setAttribute("status", "The User was removed");
        } catch (UnirestException e) {
            e.printStackTrace();
            req.setAttribute("error", "User could not be removed");
        }

        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
