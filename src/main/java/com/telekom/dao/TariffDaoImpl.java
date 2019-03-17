package com.telekom.dao;


import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public class TariffDaoImpl implements TariffDao {

    @Autowired
    private SessionFactory sessionFactory;
//private Session currentSession(){return sessionFactory.openSession();}
    @Override
    public List<Tariff> getAll() {

        Session session = sessionFactory.openSession();
        List<Tariff> tariff=	 session.createCriteria(Tariff.class).list();
        System.out.println(tariff);
        session.close();

return tariff;
        //return currentSession().createQuery("from Tariff", Tariff.class).list();
    }


    @Override
    public void add(Tariff tariff) {
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        session.saveOrUpdate(tariff);
        tx.commit();
        session.close();
    }


    @Override
    public Tariff getOne(Integer id) {
        // Reading the records from the table
        Session session = sessionFactory.openSession();
        Tariff tariff =  session.get(Tariff.class, id);
        session.close();
        return tariff;
    }


    @Override
    public void editTariff(Tariff tariff) {
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        session.update(tariff);
        tx.commit();
        session.close();
    }
    @Override
    public void deleteTariff(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        Tariff tariff = session.get(Tariff.class, id);
        session.delete(tariff);
        tx.commit();
        session.close();// close the session
    }

}
