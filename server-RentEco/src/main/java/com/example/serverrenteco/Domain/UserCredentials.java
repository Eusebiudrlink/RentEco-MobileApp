package com.example.serverrenteco.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UserCredentials {


    private String email;
    private String user_password;

    public UserCredentials() {
    }

    @JsonCreator(mode = JsonCreator.Mode.DEFAULT)
    public UserCredentials(String email, String user_password) {
        this.email = email;
        this.user_password = user_password;
    }
    public String getEmail() {
        return email;
    }
    public String getUser_password() {
        return user_password;
    }


}