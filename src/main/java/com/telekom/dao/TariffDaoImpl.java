/*
package com.telekom.dao;


import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.service.OptionService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public class TariffDaoImpl implements TariffDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return sessionFactory.openSession();
    }

    @Override
    public List<Tariff> getAll() {

        return currentSession().createQuery("from com.telekom.entity.Tariff", Tariff.class).list();
    }




    @Override
    public void add(Tariff tariff) {


        currentSession().saveOrUpdate(tariff);

    }
    @Override
    public Tariff getOne(Integer id) {
        // Reading the records from the table
        Query<Tariff> q = currentSession().createQuery("from Tariff where id = :id", Tariff.class);
       q.setParameter("id",id);
       return q.list().stream().findAny().orElse(null);
    }


    @Override
    public void editTariff(Tariff tariff) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(tariff);
        tx.commit();
        session.close();
    }

    @Override
    public void deleteTariff(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Tariff tariff = session.get(Tariff.class, id);
        session.delete(tariff);
        tx.commit();
        session.close();// close the session
    }

}
*/