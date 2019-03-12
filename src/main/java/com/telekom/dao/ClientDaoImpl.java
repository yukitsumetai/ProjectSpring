package com.telekom.dao;



import java.util.List;

import com.telekom.entity.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ClientDaoImpl implements ClientDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Client> getAll() {
        Session session = sessionFactory.openSession();
        //List<Product> products = session.createQuery("from Product").list();
        List<Client> users=	 session.createCriteria(Client.class).list();
        session.close();
        return users;
    }

    public void deleteUser(String userId) {
        Session session = sessionFactory.openSession();
        Client client =  session.get(Client.class, userId);
        session.saveOrUpdate(client);
        session.flush();
        session.close();// close the session
    }

    public void add(Client client) {
        Session session = sessionFactory.openSession();
        session.save(client);
        session.close();
    }

    public Client getOne(String clientID) {
        // Reading the records from the table
        Session session = sessionFactory.openSession();
        Client client =  session.get(Client.class, clientID);
        session.close();
        return client;
    }

}
