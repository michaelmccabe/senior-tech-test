package com.school.auth.model;

public enum Type {

    ADMIN("Administration privileges"),
    TEACHER("Teacher privileges"),
    STUDENT("Student privileges");

    private final String description;
    Type(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
