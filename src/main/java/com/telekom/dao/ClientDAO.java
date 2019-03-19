package com.telekom.dao;

import com.telekom.entity.Client;

import java.math.BigInteger;
import java.util.List;

public interface ClientDAO {

    List<Client> getAll();

    void add(Client client);

    Client getOne(BigInteger n);

}

