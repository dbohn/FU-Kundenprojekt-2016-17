package de.fuberlin.kundenprojekt.friedrich.models;

import javax.persistence.*;
import java.util.Set;

/**
 * Representation of a user role, like project manager etc.
 *
 * @author Team Friedrich
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    public int id;

    public String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "role_user", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
