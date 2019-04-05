package com.telekom.dao;


import com.telekom.entity.Tariff;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Repository
public class TariffDaoImpl2 extends PaginationDaoImpl implements TariffDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Tariff> getAll() {
        return entityManager.createQuery("select t from Tariff t").getResultList();
    }

    @Override
    public List<Tariff> getAllValid() {
        return entityManager.createQuery("select t from Tariff t where t.isValid=true").getResultList();
    }

    @Override
    public void add(Tariff tariff) {
        entityManager.persist(tariff);

    }

    @Override
    public Tariff getOne(Integer id) {
        TypedQuery<Tariff> q = entityManager.createQuery(
                "select t from Tariff t where t.id=:id", Tariff.class
        );
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);

    }

    @Override
    public List<Tariff> getPages(Integer size, Integer page) {
        TypedQuery<Tariff> q = entityManager.createQuery("select t from Tariff t", Tariff.class);
        pageCount(page, size, q);
        return q.getResultList();
    }

    @Override
    public Long getPagesCount() {
        TypedQuery<Long> q = entityManager.createQuery("Select count(o) from Tariff o", Long.class);
        return q.getSingleResult();
    }

    @Override
    public List<Tariff> getPagesValid(Integer size, Integer page) {
        TypedQuery<Tariff> q = entityManager.createQuery("select t from Tariff t where t.isValid=true", Tariff.class);
        pageCount(page, size, q);
        return q.getResultList();
    }

    @Override
    public Long getPagesValidCount() {
        TypedQuery<Long> q = entityManager.createQuery("Select count(o) from Tariff o where o.isValid=true", Long.class);
        return q.getSingleResult();
    }



}
