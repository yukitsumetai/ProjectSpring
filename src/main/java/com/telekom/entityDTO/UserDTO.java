package com.telekom.entityDTO;

import com.telekom.entity.Client;

public class UserDTO {

    private Client client;
    private String password;


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
