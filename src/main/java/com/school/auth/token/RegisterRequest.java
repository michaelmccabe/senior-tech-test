package com.school.auth.token;

import java.io.Serializable;

public class RegisterRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;
    private String details;
    private String type;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String details, String type) {
        this.username = username;
        this.password = password;
        this.details = details;
        this.type = type;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}