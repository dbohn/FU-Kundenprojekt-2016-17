package de.fuberlin.kundenprojekt.friedrich.social.messages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Team Friedrich
 */
public class Conversation {
    public Long id;
    public String title;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public List<Participant> participants;
    public List<Message> messages;

    public Conversation(Long id, String title, LocalDateTime createdAt, LocalDateTime updatedAt, List<Participant> participants, List<Message> messages) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.participants = participants;
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getlastMessageTimestamp() {
        return updatedAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public Message getLastMessage() {
        return messages.get(messages.size() - 1);
    }

    public List<Message> getMessages() {
        return messages;
    }
}
