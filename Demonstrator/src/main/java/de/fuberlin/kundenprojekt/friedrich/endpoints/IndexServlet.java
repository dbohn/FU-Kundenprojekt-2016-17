package de.fuberlin.kundenprojekt.friedrich.endpoints;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.exceptions.NoConversationsException;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.projects.ProjectsRepository;
import de.fuberlin.kundenprojekt.friedrich.search.SearchEntry;
import de.fuberlin.kundenprojekt.friedrich.search.SpaceEntry;
import de.fuberlin.kundenprojekt.friedrich.social.Configuration;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubApiUtil;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubMessages;
import de.fuberlin.kundenprojekt.friedrich.social.messages.Conversation;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handling of requests to create a dashboard
 *
 * @author Team Friedrich
 */
@WebServlet("/index")
public class IndexServlet extends BaseServlet {
    @Inject
    private UserRepository userRepository;
    private ProjectsRepository projectsRepository;
    private List<User> friends = new ArrayList<>();

    /**
     * Send a request to create a dashboard, including a friendslist, a rolelist, the user's newest message, and the user's projects
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = user(req);

        List<Conversation> conversations = null;
        Conversation con = null;


        try {
            conversations = conversationList(user);

            for (Conversation conversation : conversations) {
                if (conversation.getMessages().get(0).getUser().getUser() != user) {
                    con = conversation;
                    break;
                }
            }
        } catch (NoConversationsException e) {
            e.printStackTrace();
        }

        if (con != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu, HH:mm:ss");

            LocalDateTime a = conversations.get(0).messages.get(0).getCreatedAt();
            String time = a.format(formatter);

            req.setAttribute("hasConversation", true);
            req.setAttribute("lastConversation", con);
            req.setAttribute("updatedMessage", time);
        } else {
            req.setAttribute("hasConversation", false);
        }

        ArrayList<String> idList = new ArrayList<>();

        try {
            HttpResponse<JsonNode> friendsResponse = HumHubApiUtil.get(Configuration.getHost(), "/bcs/user/friends", Configuration.getBcsToken())
                    .queryString("user_id", user.getId())
                    .asJson();

            JSONObject message = friendsResponse.getBody().getObject().getJSONObject("message");
            JSONArray friendsList = message.getJSONArray("friends");

            for (int i = 0; i < friendsList.length(); i++) {
                JSONObject friend = friendsList.getJSONObject(i);
                idList.add(friend.getString("id"));
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }

        friends = userRepository.getUserByIdList(idList);

        req.setAttribute("username", user.username);
        req.setAttribute("projects", user.getProjects());
        req.setAttribute("userRoles", user.getRoles());
        req.setAttribute("friends", friends);

        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
    }

    /**
     * Retrieve List of conversations
     *
     * @param user
     * @return
     * @throws NoConversationsException
     */
    private List<Conversation> conversationList(User user) throws NoConversationsException {

        HumHubMessages humHubMessages = new HumHubMessages(userRepository, Configuration.getHost(), Configuration.getBcsToken());

        return humHubMessages.fetchConversations(user);
    }

}
