package de.fuberlin.kundenprojekt.friedrich.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author davidbohn
 */
@Entity
@Table(name="users")
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
}
