package de.fuberlin.kundenprojekt.friedrich.models;

import javax.persistence.*;
import java.util.Set;

/**
 * Representation of a project.
 * <p>
 * A project is aimed to simulate a project in the BCS system
 * and thus is a collection of tasks that together guide to
 * reaching a specific goal. To reach these goals, users
 * can be associated to a project. They can then use
 * HumHub to organize. We neglect tasks here, though.
 * </p>
 *
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

    /**
     * Get the record id of the project.
     *
     * @return numeric identifier
     */
    public long getId() {
        return id;
    }

    /**
     * Get the name of the project.
     * <p>
     * This is also used to identify the HumHub space in the UI.
     * </p>
     *
     * @return the name of the project
     */
    public String getName() {
        return name;
    }

    /**
     * Set the project name.
     *
     * @param name new name of the project.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the project.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the project.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get all users assigned to a project.
     *
     * @return a set of every user participating
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Set all the users, that are participating.
     *
     * @param users the participating users
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
