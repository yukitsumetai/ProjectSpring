package com.telekom.dao;

import com.telekom.entity.Condition;
import com.telekom.entity.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Repository
public class ConditionDaoImpl implements ConditionDao {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public void add(Condition condition) {
        entityManager.persist(condition);
    }
}
