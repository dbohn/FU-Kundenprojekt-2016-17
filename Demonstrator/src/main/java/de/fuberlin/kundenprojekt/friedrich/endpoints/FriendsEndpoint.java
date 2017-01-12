package de.fuberlin.kundenprojekt.friedrich.endpoints;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.Configuration;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubApiUtil;

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
@WebServlet("/friends")
public class FriendsEndpoint extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        //String id = req.getParameter("user_id");
        User user = (User) req.getSession().getAttribute("user");

        try (PrintWriter out = resp.getWriter()) {
            HttpResponse<String> response = HumHubApiUtil.get(Configuration.getHost(), "/bcs/user/friends", Configuration.getBcsToken())
                    .queryString("user_id", user.getId())
                    .asString();

            if (response.getStatus() == 200) {
                out.print(response.getBody());
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
