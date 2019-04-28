package com.telekom.dao.impl;

import com.telekom.dao.api.UserDao;
import com.telekom.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;

@Repository
@Component
public class UserDaoImpl implements UserDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getOne(Long id) {
        TypedQuery<User> q = entityManager.createQuery(
                "select t from User t where t.id=:id", User.class
        );
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public User getByLogin(BigInteger login) {
        TypedQuery<User> q = entityManager.createQuery(
                "select t from User t join t.contract t2 where t2.phoneNumber=:login", User.class
        );
        q.setParameter("login", login);
        return q.getResultList().stream().findAny().orElse(null);
    }
}
