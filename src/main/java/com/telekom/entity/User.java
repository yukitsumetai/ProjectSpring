package com.telekom.entity;

import java.io.Serializable;

import javax.persistence.*;


@Table(name = "users")
public class User implements Serializable {

    @Id
    @Column(name="login")
    private String login;
    private String password;
   private boolean enabled=true;



    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}
