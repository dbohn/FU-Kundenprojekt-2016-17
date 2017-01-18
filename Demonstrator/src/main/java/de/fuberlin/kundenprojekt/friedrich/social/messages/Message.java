package de.fuberlin.kundenprojekt.friedrich.social.messages;

import java.time.LocalDateTime;

/**
 * Representation of a single message. Has a author (see {@link Participant}),
 * id, content and timestamp.
 *
 * @author Team Friedrich
 */
public class Message {
    public Long id;
    public String content;
    private Participant user;
    private LocalDateTime createdAt;

    public Message(Long id, String content, Participant user, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Participant getUser() {
        return user;
    }

    public void setUser(Participant user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
