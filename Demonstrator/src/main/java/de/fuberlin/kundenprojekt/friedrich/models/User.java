package de.fuberlin.kundenprojekt.friedrich.models;

import de.fuberlin.kundenprojekt.friedrich.PasswordHasher;
import org.hibernate.annotations.GenericGenerator;

import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Representation of a user. A user is identified
 * by a uuid. Additionally we save a username,
 * email, phone and full name. The password
 * should be saved as a bcrypt hashed password.
 * See {@link PasswordHasher} for hashing.
 *
 * @author Team Friedrich
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String id;

    public String username;
    public String email;
    public String password;
    public String phone;
    public String full_name;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Userinfo userinfo;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private Set<Project> projects;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private Set<Role> roles;

    public User() {

    }

    public User(String username, String full_name, String email, String password, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.full_name = full_name;
    }

    @Override
    public String toString() {
        return String.format("User: %s, %s", username, full_name);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName() {
        return full_name;
    }

    public Userinfo getUserinfo() {
        if (userinfo == null) {
            Userinfo userinfo = new Userinfo(LocalDateTime.now(), LocalDateTime.now(), null);
            addUserinfo(userinfo);
        }
        return userinfo;
    }

    public void addUserinfo(Userinfo userinfo) {
        userinfo.setUser(this);
        this.userinfo = userinfo;
    }

    public void removeUserinfo() {
        if (userinfo != null) {
            userinfo.setUser(null);
            this.userinfo = null;
        }
    }

    public boolean getIsUserSynced() {
        if (userinfo == null) {
            return false;
        }

        if (userinfo.getLastSyncedAt() == null) {
            return false;
        }

        return userinfo.getUpdatedAt().isBefore(userinfo.getLastSyncedAt()) || userinfo.getUpdatedAt().isEqual(userinfo.getLastSyncedAt());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return this.username.equals(((User) obj).getUsername());
        }
        return super.equals(obj);
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
