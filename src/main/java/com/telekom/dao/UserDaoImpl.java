package com.telekom.dao;

import com.telekom.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
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
}
