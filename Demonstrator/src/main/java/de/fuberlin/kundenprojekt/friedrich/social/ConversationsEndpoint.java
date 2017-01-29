package de.fuberlin.kundenprojekt.friedrich.social;

import com.google.gson.*;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.BaseServlet;
import de.fuberlin.kundenprojekt.friedrich.exceptions.MessageReplyException;
import de.fuberlin.kundenprojekt.friedrich.exceptions.NoConversationsException;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.messages.Conversation;
import de.fuberlin.kundenprojekt.friedrich.storage.UserTypeAdapter;

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
 * Handling of requests for the chat view.
 *
 * @author Team Friedrich
 */
@WebServlet("/conversations")
public class ConversationsEndpoint extends BaseServlet {
    @Inject
    private UserRepository userRepository;

    /**
     * Handle an incoming request for a conversation or a list of all conversations.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
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

    /**
     * Handle post of a new message to a conversation.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If writing or reading the response/request fails
     */
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

    /**
     * Request a given conversation from HumHub and return the data as a JSON response.
     *
     * @param conversation The ID of the conversation
     * @param req          The incoming request
     * @param resp         The outgoing response
     * @throws IOException If anything goes wrong when writing the response
     */
    private void loadConversation(String conversation, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String since = req.getParameter("since");
        HumHubMessages humHubMessages = new HumHubMessages(userRepository, Configuration.getHost(), Configuration.getBcsToken());
        Conversation conv = humHubMessages.fetchConversation(new Long(conversation), (User) req.getSession().getAttribute("user"), since);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_DATE_TIME))).registerTypeAdapter(User.class, new UserTypeAdapter()).create();

        replyAsJson(resp, gson.toJson(conv));
    }

    /**
     * Return the messages view.
     *
     * @param req  The incoming request
     * @param resp The outgoing response
     * @throws IOException      If anything goes wrong when writing the response
     * @throws ServletException If the servlet stuff fails
     */
    private void listConversations(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("WEB-INF/messages.jsp").forward(req, resp);
    }

    /**
     * Fetch the list of conversations and return them as a JSON response
     *
     * @param request  The incoming request
     * @param response The outgoing response
     * @throws IOException      If anything goes wrong when writing the response
     * @throws ServletException If the servlet stuff fails
     */
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
