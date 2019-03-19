package com.telekom.dao;

import com.telekom.entity.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;

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
                "select t from Contract t inner join t.tariff ", Contract.class
        );
       // q.setParameter("phoneNumber", phoneNumber);
        List<Contract> c= q.getResultList();
        Contract c2 = new Contract();
        return c2;

    }
}