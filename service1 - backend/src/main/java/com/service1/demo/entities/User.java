package com.service1.demo.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id // @Id tells hibernate that this field is the key of a record in the database
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this annotation tells hibernate to generate keys automatically
    private int userId;
    // login user
    private String username;
    // password
    private String password;
    private String email;
    private boolean userActivated = false;


    @OneToMany
    private List<Apartment> apartments = new ArrayList<>();

    public User(){}

    public boolean isUserActivated() {
        return userActivated;
    }

    public void setUserActivated(boolean userActivated) {
        this.userActivated = userActivated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // method to retrieve user ID

    public int getUserId() {
        return userId;
    }

    // method to change the user ID

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Apartment> getAppartents() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    // function to retrieve the user name

    public String getUsername() {
        return username;
    }
    // function to change the login of a user

    public void setUsername(String username) {
        this.username = username;
    }
    // function to retrieve the password
    public String getPassword() {
        return password;
    }
    // function to change values
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Apartment> getApartment() {
    }

    public List<Apartment> getApartments() {
        return apartments;
    }
}
