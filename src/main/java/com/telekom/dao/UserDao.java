package com.telekom.dao;

import com.telekom.entity.User;

public interface UserDao {
    void add(User user);
    User getOne(Long id);
    User getByLogin(String login);
}
