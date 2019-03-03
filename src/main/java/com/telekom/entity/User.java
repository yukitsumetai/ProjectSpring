package com.telekom.entity;

public class User {
    private String name;
    private String email;
    static private int id;

    public User() {
        id++;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
       id++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
