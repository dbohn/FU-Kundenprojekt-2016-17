package de.fuberlin.kundenprojekt.friedrich.social;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.fuberlin.kundenprojekt.friedrich.UserRepository;
import de.fuberlin.kundenprojekt.friedrich.exceptions.MessageReplyException;
import de.fuberlin.kundenprojekt.friedrich.exceptions.NoConversationsException;
import de.fuberlin.kundenprojekt.friedrich.models.User;
import de.fuberlin.kundenprojekt.friedrich.social.messages.Conversation;
import de.fuberlin.kundenprojekt.friedrich.social.messages.Message;
import de.fuberlin.kundenprojekt.friedrich.social.messages.Participant;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team Friedrich
 */
public class HumHubMessages {

    private UserRepository userRepository;
    private String host;
    private String bcsToken;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public HumHubMessages(UserRepository userRepository, String host, String bcsToken) {
        this.userRepository = userRepository;
        this.host = host;
        this.bcsToken = bcsToken;
    }

    public Conversation fetchConversation(Long conversationId, User user) {
        try {
            HttpResponse<JsonNode> response = HumHubApiUtil.get(host, "/bcs/messages/show", bcsToken)
                    .queryString("bcs_id", user.getId())
                    .queryString("message", conversationId)
                    .asJson();
            return extractConversation(response.getBody().getObject().getJSONObject("message"));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Conversation> fetchConversations(User user) throws NoConversationsException {
        List<Conversation> conversations = new ArrayList<>();
        try {
            HttpResponse<JsonNode> response = HumHubApiUtil.get(host, "/bcs/messages", bcsToken)
                    .queryString("bcs_id", user.getId())
                    .asJson();
            JSONObject body = response.getBody().getObject();

            if (!body.has("message")) {
                throw new NoConversationsException("No conversations found");
            }

            JSONArray messages = body.getJSONArray("message");

            for (int i = 0; i < messages.length(); i++) {
                JSONObject message = messages.getJSONObject(i);
                Conversation conversation = extractConversation(message);
                conversations.add(conversation);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new NoConversationsException("Unable to call HumHub API");
        }
        return conversations;
    }

    public void postMessage(String conversationId, String message, User user) throws MessageReplyException {
        try {
            HttpResponse<JsonNode> response = HumHubApiUtil.post(this.host, "/bcs/messages/add", this.bcsToken)
                    .field("bcs_id", user.id)
                    .field("message", conversationId)
                    .field("content", message)
                    .asJson();

            if (response.getStatus() != 200) {
                JSONObject respObj = response.getBody().getObject();
                throw new MessageReplyException(respObj.getString("message"));
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new MessageReplyException(e.getMessage());
        }
    }

    private Conversation extractConversation(JSONObject message) {
        JSONArray participantArray = message.getJSONArray("users");
        JSONObject messageInfo = message.getJSONObject("message");
        List<Participant> participants = mapParticipants(participantArray);

        List<Message> messages = new ArrayList<>();

        if (message.has("last_entry")) {
            JSONObject entry = message.getJSONObject("last_entry");

            messages.add(parseMessage(participants, entry));
        }

        if (message.has("entries")) {
            JSONArray entries = message.getJSONArray("entries");
            for (int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);

                messages.add(parseMessage(participants, entry));
            }
        }

        return new Conversation(
                messageInfo.getLong("id"),
                messageInfo.getString("title"),
                LocalDateTime.parse(messageInfo.getString("created_at"), formatter),
                LocalDateTime.parse(messageInfo.getString("updated_at"), formatter),
                participants,
                messages);
    }

    private Message parseMessage(List<Participant> participants, JSONObject entry) {
        Participant lastParticipant = getMessageAuthor(participants, entry);

        return new Message(
                entry.getLong("id"),
                entry.getString("content"),
                lastParticipant,
                LocalDateTime.parse(entry.getString("created_at"), formatter)
        );
    }

    private Participant getMessageAuthor(List<Participant> participants, JSONObject lastEntry) {
        Optional<Participant> participantOptional = participants
                .stream()
                .filter(participant -> participant.getSocialId() == lastEntry.getLong("user_social_id"))
                .findFirst();

        Participant lastParticipant = null;
        if (participantOptional.isPresent()) {
            lastParticipant = participantOptional.get();
        }
        return lastParticipant;
    }

    private List<Participant> mapParticipants(JSONArray participantArray) {
        List<Participant> participants = new ArrayList<>();
        for (int i = 0; i < participantArray.length(); i++) {
            JSONObject participantObject = participantArray.getJSONObject(i);

            User user = null;
            // TODO: this should in fact be a collected query to prevent the n+1 problem
            if (!participantObject.isNull("id")) {
                //String participantId = participantObject.getString("id");
                user = userRepository.getUserById(participantObject.getString("id"));
            }

            Participant participant = new Participant(
                    user,
                    participantObject.getString("displayname"),
                    participantObject.getLong("social_id")
            );

            participants.add(participant);
        }
        return participants;
    }
}
