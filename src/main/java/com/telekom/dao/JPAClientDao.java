package com.telekom.dao;

import com.telekom.entity.Client;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Transactional(readOnly = true)
@Component
public class JPAClientDao implements ClientDAO {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;


    public List<Client> getAll() {
        return entityManager.createQuery("select c from Client c", Client.class
        ).getResultList();
    }


    @Transactional
    public void add(Client client) {
        entityManager.persist(client);
    }


    public Client getOne(String email) {
        TypedQuery<Client> q = entityManager.createQuery("select c from Client c where c.email= :email", Client.class
        );
        q.setParameter("email", email);
        return q.getResultList().stream().findAny().orElse(null);
    }

}
