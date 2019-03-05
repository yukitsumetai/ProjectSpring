package com.telekom.dao;


import com.telekom.entity.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;


@Component
public class TestDAO implements ClientDAO {


    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return sessionFactory.openSession();
    }


    public List<Client> getAll() {
        return currentSession().createQuery("from com.telekom.entity.Client", Client.class).list();
    }




}