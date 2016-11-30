package de.fuberlin.kundenprojekt.friedrich.models;

import javax.persistence.*;

/**
 * @author davidbohn
 */
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String username;
    public String email;
    public String password;
    public String phone;
    public String full_name;

    public User() {

    }

    public User(String username, String full_name, String email, String password, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.full_name = full_name;
    }
}
