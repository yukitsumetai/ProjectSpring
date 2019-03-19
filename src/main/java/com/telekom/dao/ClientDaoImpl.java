package com.telekom.dao;


import com.telekom.entity.Address;
import com.telekom.entity.Client;
import com.telekom.entity.Option;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;

@Component
@Repository
public class ClientDaoImpl implements ClientDAO {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Client> getAll() {
        return entityManager.createQuery("select t from Client t join fetch t.contract").getResultList();
    }


    @Override
    public void add(Client client) {
       entityManager.persist(client);
    }



    public Client getOne(BigInteger phoneNumber) {
        TypedQuery<Client> q = entityManager.createQuery(
                "select t from Client t join fetch t.contract as c  where c.phoneNumber=:phoneNumber", Client.class);
        q.setParameter("phoneNumber", phoneNumber);
        return q.getResultList().stream().findAny().orElse(null);

    }

    public Client getOne(Integer id) {
        TypedQuery<Client> q = entityManager.createQuery(
                "select t from Client t where t.id=:id", Client.class
        );
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);

    }


    public void editClient(Client client) {
        entityManager.persist(client);
    }


}

