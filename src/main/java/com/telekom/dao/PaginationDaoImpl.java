package com.telekom.dao;

import com.telekom.entity.Tariff;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public class PaginationDaoImpl< T extends Serializable > {
    private Class< T > clazz;
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;
    void pageCount(Integer page, Integer size, Query q){
        int pageNumber = page;
        int pageSize = size;
        q.setFirstResult((pageNumber-1) * pageSize);
        q.setMaxResults(pageSize);
    }

    public List<T> getPages(Integer size, Integer page) {
       Query q = entityManager.createQuery("from " + clazz.getName());
        pageCount(page, size, q);
        return q.getResultList();
    }

    public Long getPagesCount(){
        Query q = entityManager.createQuery("Select count(f) from"+ clazz.getName()+"f");
        return (Long) (long) q.getSingleResult();
    }

}
