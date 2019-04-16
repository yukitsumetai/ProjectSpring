package com.telekom.dao;

import com.telekom.model.entity.User;

import java.math.BigInteger;

public interface UserDao {
    void add(User user);
    User getOne(Long id);
    User getByLogin(BigInteger login);
}
