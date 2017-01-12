package de.fuberlin.kundenprojekt.friedrich.endpoints;

import com.google.gson.*;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.exceptions.MessageReplyException;
import de.fuberlin.kundenprojekt.friedrich.exceptions.NoConversationsException;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.Configuration;
import de.fuberlin.kundenprojekt.friedrich.social.HumHubMessages;
import de.fuberlin.kundenprojekt.friedrich.social.messages.Conversation;
import de.fuberlin.kundenprojekt.friedrich.storage.UserTypeAdapter;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Team Friedrich
 */
@WebServlet("/conversations")
public class ConversationsEndpoint extends BaseServlet {
    @Inject
    private UserRepository userRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String conversationId = req.getParameter("conversation");

        String conversationList = req.getParameter("conversations");

        if (conversationId != null) {
            loadConversation(conversationId, req, resp);
        } else if (conversationList != null) {
            jsonConversationList(req, resp);
        } else {
            listConversations(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = req.getParameter("message");
        String conversationId = req.getParameter("conversation");

        User user = user(req);

        HumHubMessages humHubMessages = new HumHubMessages(userRepository, Configuration.getHost(), Configuration.getBcsToken());

        try {
            humHubMessages.postMessage(conversationId, message, user);
            replyAsJson(resp, "{\"message\":\"message submitted\"}");
        } catch (MessageReplyException e) {
            replyAsJsonError(resp, e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadConversation(String conversation, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HumHubMessages humHubMessages = new HumHubMessages(userRepository, Configuration.getHost(), Configuration.getBcsToken());
        Conversation conv = humHubMessages.fetchConversation(new Long(conversation), (User) req.getSession().getAttribute("user"));

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_DATE_TIME))).registerTypeAdapter(User.class, new UserTypeAdapter()).create();

        replyAsJson(resp, gson.toJson(conv));
    }

    private void listConversations(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("./messages.jsp").forward(req, resp);
    }

    private void jsonConversationList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HumHubMessages humHubMessages = new HumHubMessages(userRepository, Configuration.getHost(), Configuration.getBcsToken());
        try {
            List<Conversation> conversationList = humHubMessages.fetchConversations(user(request));

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_DATE_TIME)))
                    .registerTypeAdapter(User.class, new UserTypeAdapter()).create();

            replyAsJson(response, gson.toJson(conversationList));
        } catch (NoConversationsException e) {
            replyAsJsonError(response, e.getMessage());
            e.printStackTrace();
        }
    }
}
