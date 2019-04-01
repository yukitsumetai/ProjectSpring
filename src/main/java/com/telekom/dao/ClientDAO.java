package com.telekom.dao;

import com.telekom.entity.Client;

import java.math.BigInteger;
import java.util.List;

public interface ClientDAO  extends PaginationDao {
    List<Client> getAll();
    void add(Client client);
    Client getOne(BigInteger n);
    Client getOne(Integer n);
    Client getOne(Long userId);
    boolean getOneByEmail(String email);
    boolean getOneByPassport(Integer id);
    List<Client> getPages(Integer size, Integer page);
    Long getPagesCount();
}

