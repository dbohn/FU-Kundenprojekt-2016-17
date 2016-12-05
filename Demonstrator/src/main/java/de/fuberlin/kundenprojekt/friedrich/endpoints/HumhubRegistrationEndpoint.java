package de.fuberlin.kundenprojekt.friedrich.endpoints;

import com.mashape.unirest.http.JsonNode;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.Configuration;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubUserRepository;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Team Friedrich
 */
public class HumhubRegistrationEndpoint extends HttpServlet {

    @Inject
    private UserRepository userRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp(resp, userRepository.getUserById("1").toString());
        //super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        User user = userRepository.getUserById(id);
        //resp(resp, user.toString());

        HumHubUserRepository ur = new HumHubUserRepository(Configuration.getBcsToken(), Configuration.getHost());

        JsonNode status = ur.add(user);
        if (status != null) {
            req.setAttribute("status", status.getObject().get("message"));
        }
        req.getRequestDispatcher("/users.jsp").forward(req, resp);
    }

    private void resp(HttpServletResponse resp, String msg) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<p>" + msg + "</p>");
        out.close();
    }
}
