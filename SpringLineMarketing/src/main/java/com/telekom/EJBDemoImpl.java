package com.telekom;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;


@Stateless
public class EJBDemoImpl implements EJBDemo {
    @EJB
    Client client;


    public boolean login(String login, String psw) {

        if (login.equals("1") && psw.equals("2")) {
            client.setRegistered(true);
            client.setLogin(login);
            return true;
        }
        return false;
    }

    public String getMessage(String login) {
        if (client.isRegistered()) {
            client.addCount();
            if (client.getCount() < 4) {
                return "Hello " + login + " count:" + client.getCount();
            } else {
                client.setCount(0);
                client.setRegistered(false);

            }
        }
        return "Пройдите регистрацию";
    }
}
