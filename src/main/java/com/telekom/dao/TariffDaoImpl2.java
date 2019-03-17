package com.telekom.dao;
/*

import com.telekom.entity.Tariff;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Repository
public class TariffDaoImpl2 implements TariffDao {

    @PersistenceContext(unitName="entityManagerFactory")
    private EntityManager entityManager;
//private Session currentSession(){return sessionFactory.openSession();}
    @Override
    public List<Tariff> getAll() {
        return entityManager.createQuery("select t from Tariff t").getResultList();
    }


    @Override
    public void add(Tariff tariff) {
        entityManager.persist(tariff);
        /*
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        session.saveOrUpdate(tariff);
        tx.commit();
        session.close();
        */
    //}
/*

    @Override
    public Tariff getOne(Integer id) {
    TypedQuery<Tariff> q=entityManager.createQuery(
            "select t from Tariff t where t.id=:id", Tariff.class
    );
    q.setParameter("id", id);
    return  q.getResultList().stream().findAny().orElse(null);
        /*
        Session session = sessionFactory.openSession();
        Tariff tariff =  session.get(Tariff.class, id);
        session.close();
        return tariff;
        */
   // }

/*
    @Override
    public void editTariff(Tariff tariff) {
        entityManager.persist(tariff);
    }
    @Override
    public void deleteTariff(Integer id) {

        TypedQuery<Tariff> q=entityManager.createQuery(
                "select t from Tariff t where t.id=:id", Tariff.class
        );
        q.setParameter("id", id);
        //return  q.getResultList().stream().findAny().orElse(null);
        /*
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        Tariff tariff = session.get(Tariff.class, id);
        session.delete(tariff);
        tx.commit();
        session.close();// close the session*/
    //}

//}
