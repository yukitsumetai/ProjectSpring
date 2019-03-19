package com.telekom.dao;


import com.telekom.entity.Option;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Repository
public class OptionDaoImpl implements OptionDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Option> getAll() {
        return entityManager.createQuery("select t from Option t").getResultList();
    }


    @Override
    public void add(Option option) {
        entityManager.persist(option);

    }


    @Override
    public Option getOne(Integer id) {
        TypedQuery<Option> q = entityManager.createQuery(
                "select t from Option t where t.id=:id", Option.class
        );
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);

    }


    @Override
    public void editOption(Option option) {
        entityManager.persist(option);
    }

    @Override
    public void deleteOption(Integer id) {

        TypedQuery<Option> q = entityManager.createQuery(
                "select t from Option t where t.id=:id", Option.class
        );
        q.setParameter("id", id);
        Option t=q.getResultList().stream().findAny().orElse(null);
        entityManager.remove(t);
    }

}

