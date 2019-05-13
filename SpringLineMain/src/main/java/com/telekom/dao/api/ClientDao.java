package com.telekom.dao.api;

import com.telekom.model.entity.Client;

import java.math.BigInteger;
import java.util.List;

public interface ClientDao extends PaginationDao {
    void add(Client client);
    Client getOne(BigInteger n);
    Client getOne(Integer n);
    Client getOne(Long userId);
    boolean getOneByEmail(String email);
    boolean getOneByPassport(Integer id);
    List<Client> getPages(Integer size, Integer page);
    Long getPagesCount();
}

