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
import java.io.PrintWriter;
import java.util.List;

/**
 * Handling of requests to create new chat conversations.
 *
 * @author Team Friedrich
 */
@WebServlet("conversations/create")
public class CreateConversationEndpoint extends BaseServlet {

    /**
     * Send a request to create a new conversation to HumHub.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String message = req.getParameter("message");

        User user = user(req);

        List<String> recipientsList = getParameterList("recipients", req);

        String recipients = String.join(",", recipientsList);
        try (PrintWriter out = resp.getWriter()) {
            HttpResponse<String> response = HumHubApiUtil.post(Configuration.getHost(), "/bcs/messages/create", Configuration.getBcsToken())
                    .field("user_id", user.getId())
                    .field("title", title)
                    .field("message", message)
                    .field("recipient", recipients)
                    .asString();

            resp.setStatus(response.getStatus());
            out.print(response.getBody());
        } catch (UnirestException e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
