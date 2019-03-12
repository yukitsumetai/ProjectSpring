package com.telekom.dao;


import com.telekom.entity.Option;
import com.telekom.entity.Option;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OptionDaoImpl implements OptionDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Option> getAll() {
        Session session = sessionFactory.openSession();
        List<Option> option=	 session.createCriteria(Option.class).list();
        System.out.println(option);
        session.close();
        return option;
    }


    @Override
    public void add(Option option) {
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        session.save(option);
        tx.commit();
        session.close();
    }

    @Override
    public Option getOne(Integer id) {
        Session session = sessionFactory.openSession();
        Option option =  session.get(Option.class, id);
        session.close();
        return option;
    }

}
