package com.telekom.dao.impl;


import com.telekom.dao.api.OptionDao;
import com.telekom.model.entity.Option;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Repository
public class OptionDaoImpl extends PaginationDaoImpl implements OptionDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Option> getAll() {
        return entityManager.createQuery("select t from Option t").getResultList();
    }
    @Override
    public List<Option> getAllValid() {
        return entityManager.createQuery("select t from Option t where t.isValid=true ").getResultList();
    }

    @Override
    public List<Option> findByTariffParents(Integer id) {
        TypedQuery<Option> q= entityManager.createQuery("select o from Option o  join o.compatibleTariffs as t2 where t2.id=:id and o.isValid=true and o.id not in(select o from Option o join o.parent) ", Option.class);
        q.setParameter("id", id);
        return q.getResultList();
    }

    @Override
    public List<Option> findByTariffChildren(Integer id) {
        TypedQuery<Option> q= entityManager.createQuery("select o from Option o  join o.compatibleTariffs as t2 where t2.id=:id and o.isValid=true and o.id in(select o from Option o join o.parent) ", Option.class);
        q.setParameter("id", id);
        return q.getResultList();
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
    public List<Option> getPages(Integer size, Integer page) {
        TypedQuery<Option> q = entityManager.createQuery("select t from Option t", Option.class);
        pageCount(page, size, q);
        return q.getResultList();
    }

    @Override
    public Long getPagesCount() {
        TypedQuery<Long> q = entityManager.createQuery("Select count(o) from Option o", Long.class);
        return q.getSingleResult();
    }

    @Override
    public List<Option> getAllNoParent(Integer size, Integer page) {
        TypedQuery<Option> q = entityManager.createQuery("select t from Option t  where t.id not in(select t from Option t join t.parent)", Option.class);
        pageCount(page, size, q);
        return q.getResultList();
    }

    @Override
    public Long getPagesCountNoParent() {
        TypedQuery<Long> q = entityManager.createQuery("Select count(o) from Option o where o.id not in(select t from Option t join t.parent)", Long.class);
        return q.getSingleResult();
    }

    @Override
    public List<Option> getAllNoChildrenAndParent(Integer size, Integer page) {
        TypedQuery<Option> q = entityManager.createQuery("select t from Option t  where t.id not in(select t from Option t join t.children) and t.id not in(select t from Option t join t.parent)", Option.class);
        pageCount(page, size, q);
        return q.getResultList();
    }

    @Override
    public Long getPagesCountNoParentNoChildren() {
        TypedQuery<Long> q = entityManager.createQuery("select count(t) from Option t  where t.id not in(select t from Option t join t.children) and t.id not in(select t from Option t join t.parent)", Long.class);
        return q.getSingleResult();
    }

    @Override
    public List<Option> getAllNoChildrenAndParentExisting(Integer size, Integer page, Integer id) {
        TypedQuery<Option> q = entityManager.createQuery("select t from Option t  where t.id not in(select t from Option t join t.children) and t.id not in(select t from Option t join t.parent) or t.id in(select t from Option t join t.parent p where p.id=:id)", Option.class);
        q.setParameter("id", id);
        pageCount(page, size, q);
        return q.getResultList();
    }

    @Override
    public Long getPagesNoChildrenAndParentExisting(Integer id) {
        TypedQuery<Long> q = entityManager.createQuery("select count(t) from Option t  where t.id not in(select t from Option t join t.children) and t.id not in(select t from Option t join t.parent) or t.id in(select t from Option t join t.parent p where p.id=:id)", Long.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

    @Override
    public List<Option> getAllNoParentNoGroup(Integer size, Integer page) {
        TypedQuery<Option> q = entityManager.createQuery( "select t from Option t where t.id not in(select t from Option t join t.parent) and t.id not in(select t from Option t join t.group)", Option.class);
        pageCount(page, size, q);
        return q.getResultList();
    }

    @Override
    public Long getPagesCountNoParentNoGroup() {
        TypedQuery<Long> q = entityManager.createQuery("select count(t) from Option t where t.id not in(select t from Option t join t.parent) and t.id not in(select t from Option t join t.group)", Long.class);
        return q.getSingleResult();
    }

    @Override
    public List<Option> getAllNoParentNoGroupExisting(Integer size, Integer page, Integer id) {
        TypedQuery<Option> q = entityManager.createQuery( "select t from Option t where t.id not in(select t from Option t join t.parent) and t.id not in(select t from Option t join t.group) or t.id in(select t from Option t join t.group p where p.id=:id)", Option.class);
        q.setParameter("id", id);
        pageCount(page, size, q);
        return q.getResultList();
    }

    @Override
    public Long getPagesCountNoParentNoGroupExisting(Integer id) {
        TypedQuery<Long> q = entityManager.createQuery("select count(t) from Option t where t.id not in(select t from Option t join t.parent) and t.id not in(select t from Option t join t.group)  or t.id in(select t from Option t join t.group p where p.id=:id)", Long.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }
}

