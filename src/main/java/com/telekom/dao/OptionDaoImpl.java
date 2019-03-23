package com.telekom.dao;


import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
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
    public List<Option> getAllValidNoParent() {
        return entityManager.createQuery("select t from Option t  where t.isValid=true and t.id not in(select t from Option t join t.parent)").getResultList();
    }

    @Override
    public List<Option> getAllValidNoChildrenAndParent() {
        return entityManager.createQuery("select t from Option t  where t.isValid=true and t.id not in(select t from Option t join t.children) and t.id not in(select t from Option t join t.parent)").getResultList();
    }


    @Override
    public List<Option> findByTariff(Integer id) {
        TypedQuery<Option> q= entityManager.createQuery("select o from Option o join fetch o.compatibleTariffs as t where t.id=:id and o.isValid=true", Option.class);
        q.setParameter("id", id);
        List<Option> options=q.getResultList();
        return options;
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
        Option c=q.getResultList().stream().findAny().orElse(null);
        return c;

    }





}

