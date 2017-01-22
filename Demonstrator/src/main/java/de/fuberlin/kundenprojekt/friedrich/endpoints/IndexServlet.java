package de.fuberlin.kundenprojekt.friedrich.endpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.exceptions.NoConversationsException;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.projects.ProjectsRepository;
import de.fuberlin.kundenprojekt.friedrich.social.Configuration;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubMessages;
import de.fuberlin.kundenprojekt.friedrich.social.messages.Conversation;
import de.fuberlin.kundenprojekt.friedrich.storage.UserTypeAdapter;
import de.fuberlin.kundenprojekt.friedrich.models.Project;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDateTime;

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

        for(int i=0; i< conversations.size();i++){
            if(conversations.get(i).getMessages().get(0).getUser().getUser()!=user){
                con = conversations.get(i);
                break;
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu, HH:mm:ss");

        LocalDateTime a = conversations.get(0).messages.get(0).getCreatedAt();
        String time = a.format(formatter);

        req.setAttribute("lastConversation",con);
        req.setAttribute("username",user.username);
        req.setAttribute("updatedMessage",time);
        req.setAttribute("projects", user.getProjects());

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
