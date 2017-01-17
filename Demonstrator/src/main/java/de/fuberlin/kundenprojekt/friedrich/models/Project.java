package de.fuberlin.kundenprojekt.friedrich.models;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Team Friedrich
 */
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue
    public long id;

    public String name;

    public String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "project_user", joinColumns = {@JoinColumn(name = "project_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;

    public Project() {
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
