package com.telekom.dao.impl;



import com.telekom.dao.api.ClientDao;
import com.telekom.model.entity.Client;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;

@Component
@Repository
public final class ClientDaoImpl extends PaginationDaoImpl implements ClientDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public void add(Client client) {
       entityManager.persist(client);
    }


    @Override
    public Client getOne(BigInteger phoneNumber) {
        TypedQuery<Client> q = entityManager.createQuery(
                "select t from Client t join t.contract  where t in (select t from Client t join t.contract as c where c.phoneNumber=:phoneNumber)", Client.class);
        q.setParameter("phoneNumber", phoneNumber);
        return q.getResultList().stream().findAny().orElse(null);
        //"select t from Client t join fetch t.contract as c  where c.phoneNumber=:phoneNumber"
    }
    @Override
    public Client getOne(Long userId) {
        TypedQuery<Client> q = entityManager.createQuery(
                "select t from Client t join t.contract join t.user u where u.id=:id", Client.class);
        q.setParameter("id", userId);
        return q.getResultList().stream().findAny().orElse(null);
    }
    @Override
    public Client getOne(Integer id) {
        TypedQuery<Client> q = entityManager.createQuery(
                "select t from Client t where t.id=:id", Client.class
        );
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);

    }
    @Override
    public boolean getOneByPassport(Integer id) {
        TypedQuery<Client> q = entityManager.createQuery(
                "select t from Client t where t.passport=:id", Client.class
        );
        q.setParameter("id", id);
        Client c= q.getResultList().stream().findAny().orElse(null);
        return c != null;

    }
    @Override
    public boolean getOneByEmail(String email) {
        TypedQuery<Client> q = entityManager.createQuery(
                "select t from Client t where t.email=:email", Client.class
        );
        q.setParameter("WEB-INF/resource/email", email);
        Client c= q.getResultList().stream().findAny().orElse(null);
        return c!=null;

    }


    @Override
    public List<Client> getPages(Integer size, Integer page) {
        TypedQuery<Client> q = entityManager.createQuery("select t from Client t", Client.class);
        pageCount(page, size, q);
        return q.getResultList();
    }

    @Override
    public Long getPagesCount() {
        TypedQuery<Long> q = entityManager.createQuery("Select count(o) from Client o", Long.class);
        return q.getSingleResult();
    }


}

