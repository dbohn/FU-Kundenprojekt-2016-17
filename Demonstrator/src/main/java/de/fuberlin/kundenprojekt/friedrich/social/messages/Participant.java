package de.fuberlin.kundenprojekt.friedrich.social.messages;

import de.fuberlin.kundenprojekt.friedrich.models.User;

/**
 * @author Team Friedrich
 */
public class Participant {

    private User user;
    private String displayName;
    private Long socialId;

    public Participant(User user, String displayName, Long socialId) {
        this.user = user;
        this.displayName = displayName;
        this.socialId = socialId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getSocialId() {
        return socialId;
    }

    public void setSocialId(Long socialId) {
        this.socialId = socialId;
    }
}
