package de.fuberlin.kundenprojekt.friedrich.social;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.fuberlin.kundenprojekt.friedrich.BaseServlet;
import de.fuberlin.kundenprojekt.friedrich.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Endpoint to retrieve a list of friends of the authenticated user.
 *
 * @author Team Friedrich
 */
@WebServlet("/friends")
public class FriendsEndpoint extends BaseServlet {

    /**
     * Request a list of all friends of the authenticated user
     * and send them as a JSON response to the client.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = user(req);

        try {
            HttpResponse<String> response = HumHubApiUtil.get(Configuration.getHost(), "/bcs/user/friends", Configuration.getBcsToken())
                    .queryString("user_id", user.getId())
                    .asString();

            replyAsJson(resp, response.getBody(), response.getStatus());
        } catch (UnirestException e) {
            e.printStackTrace();
            replyAsJsonError(resp, e.getMessage());
        }
    }
}
