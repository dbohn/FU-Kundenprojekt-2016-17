package de.fuberlin.kundenprojekt.friedrich.endpoints;

import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.exceptions.NoConversationsException;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.projects.ProjectsRepository;
import de.fuberlin.kundenprojekt.friedrich.social.Configuration;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubMessages;
import de.fuberlin.kundenprojekt.friedrich.social.messages.Conversation;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Team Friedrich
 */
@WebServlet("/index")
public class IndexServlet extends BaseServlet {
    @Inject
    private UserRepository userRepository;
    private ProjectsRepository projectsRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = user(req);

        List<Conversation> conversations = jsonConversationList(req, resp);

        Conversation con = null;

        for (Conversation conversation : conversations) {
            if (conversation.getMessages().get(0).getUser().getUser() != user) {
                con = conversation;
                break;
            }
        }

        if (conversations.size() > 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu, HH:mm:ss");

            LocalDateTime a = conversations.get(0).messages.get(0).getCreatedAt();
            String time = a.format(formatter);

            req.setAttribute("hasConversation", true);
            req.setAttribute("lastConversation", con);
            req.setAttribute("updatedMessage", time);
        } else {
            req.setAttribute("hasConversation", false);
        }

        req.setAttribute("username", user.username);
        req.setAttribute("projects", user.getProjects());
        req.setAttribute("userRoles", user.getRoles());


        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
    }

    private List<Conversation> jsonConversationList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HumHubMessages humHubMessages = new HumHubMessages(userRepository, Configuration.getHost(), Configuration.getBcsToken());

        List<Conversation> conversationList = null;

        try {
            conversationList = humHubMessages.fetchConversations(user(request));
        } catch (NoConversationsException e) {
            replyAsJsonError(response, e.getMessage());

            e.printStackTrace();
        }
        return conversationList;
    }

}
