package com.school.auth.model;

import javax.persistence.*;

@Table(name = "accounts")
@Entity
public class Account {

    @Id
    private String username;

    private String password;

    private String details;

    private String type;

    public Account() {
    }

    public Account(String username, String password, String details, String type) {
        this.username = username;
        this.details = details;
        this.password = password;
        this.type = type;
        if(type==null || type.isEmpty()) {
            this.type = Type.STUDENT.name();
        }
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.type = Type.STUDENT.name();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
