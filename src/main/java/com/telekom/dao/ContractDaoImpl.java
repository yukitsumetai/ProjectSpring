package com.telekom.dao;

import com.telekom.entity.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;

@Repository
public class ContractDaoImpl implements ContractDao {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;


    @Override
    public void add(Contract contract) {

        entityManager.persist(contract);
    }

    @Override
    public Contract getOne(BigInteger phoneNumber) {
        TypedQuery<Contract> q = entityManager.createQuery(
                "select t from Contract t where t.client.phoneNumber=:phoneNumber", Contract.class
        );
        q.setParameter("phoneNumber", phoneNumber);
        return q.getResultList().stream().findAny().orElse(null);

    }
}