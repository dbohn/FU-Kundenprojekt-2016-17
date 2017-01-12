package de.fuberlin.kundenprojekt.friedrich.endpoints;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Team Friedrich
 */
@WebServlet("conversations/create")
public class CreateConversationEndpoint extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String message = req.getParameter("message");

        User user = (User) req.getSession().getAttribute("user");

        List<String> recipientsList = this.getRecipients(req);

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

    private List<String> getRecipients(HttpServletRequest req) {
        List<String> types = new ArrayList<>();

        String[] typesArray = req.getParameterValues("recipients[]");

        if (typesArray != null) {
            types = Arrays.asList(typesArray);
        }
        return types;
    }
}
