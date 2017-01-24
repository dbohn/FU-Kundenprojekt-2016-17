package de.fuberlin.kundenprojekt.friedrich.models;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Timestamps for a certain user (created_at, updated_at)
 *
 * @author Team Friedrich
 */
@Entity
public class Userinfo {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    LocalDateTime created_at;
    LocalDateTime updated_at;
    LocalDateTime last_synced_at;

    public Userinfo() {
    }

    public Userinfo(LocalDateTime created_at, LocalDateTime updated_at, LocalDateTime last_synced_at) {
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.last_synced_at = last_synced_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdatedAt() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getLastSyncedAt() {
        return last_synced_at;
    }

    public void setLastSyncedAt(LocalDateTime last_synced_at) {
        this.last_synced_at = last_synced_at;
    }
}
