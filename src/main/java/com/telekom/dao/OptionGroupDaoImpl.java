package com.telekom.dao;

import com.telekom.entity.Option;
import com.telekom.entity.OptionGroup;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
@Component
@Repository
public class OptionGroupDaoImpl implements OptionGroupDao {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<OptionGroup> getAll() {
        return entityManager.createQuery("select t from OptionGroup t").getResultList();
    }

    @Override
    public List<OptionGroup> getAllValid() {
        return entityManager.createQuery("select t from OptionGroup t where t.isValid=true").getResultList();
    }

    @Override
    public void add(OptionGroup tariff) {
        entityManager.persist(tariff);
    }
    @Override
    public List<OptionGroup> getPages(Integer size, Integer page) {
        TypedQuery<OptionGroup> q= entityManager.createQuery("select t from OptionGroup t where t.isValid=true", OptionGroup.class);
        int pageNumber = page;
        int pageSize = size;
        q.setFirstResult((pageNumber-1) * pageSize);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }
@Override
    public Long getPagesCount(){
        TypedQuery<Long> q = entityManager.createQuery("Select count(f.id) from OptionGroup f", Long.class);
        return q.getSingleResult();
    }

    @Override
    public List<OptionGroup> findByName(String name) {
        TypedQuery<OptionGroup> q= entityManager.createQuery("select o from OptionGroup o where o.name like :name and o.isValid=true", OptionGroup.class);
        q.setParameter("name",  "%"+name+"%");
        List<OptionGroup> optionGroups=q.getResultList();
        return optionGroups;
    }



    @Override
    public OptionGroup getOne(Integer id) {
        TypedQuery<OptionGroup> q = entityManager.createQuery(
                "select t from OptionGroup t where t.id=:id", OptionGroup.class
        );
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);

    }

}
