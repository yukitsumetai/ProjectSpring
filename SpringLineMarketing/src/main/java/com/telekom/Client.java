package com.telekom;


import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@Stateful
@LocalBean
public class Client implements Serializable {
    boolean registered;
    int count=0;
    String login;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public int getCount() {
        return this.count;
    }

    public void addCount() {
        this.count++;
    }
    public void setCount(int count) {
        this.count=count;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
