package com.service1.demo.object;

public class UsernameAndPasswordObject {
    // the username field that stores the user login sent during registration via the form
    private String username;
    //place in which is stored the password of the user sent during registration through the form
    private String password;
    private String email;


    public UsernameAndPasswordObject() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
