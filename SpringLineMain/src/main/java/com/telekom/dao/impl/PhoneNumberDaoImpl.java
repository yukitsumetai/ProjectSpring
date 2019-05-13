package com.telekom.dao.impl;

import com.telekom.dao.api.PhoneNumberDao;
import com.telekom.model.entity.FreePhoneNumber;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;

@Component
@Repository
public class PhoneNumberDaoImpl extends PaginationDaoImpl implements PhoneNumberDao {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public void deleteNumber(BigInteger phoneNumber) {
        TypedQuery<FreePhoneNumber> q = entityManager.createQuery(
                "select t from FreePhoneNumber t where t.phoneNumber=:phoneNumber", FreePhoneNumber.class
        );
        q.setParameter("phoneNumber", phoneNumber);
        FreePhoneNumber t = q.getResultList().stream().findAny().orElse(null);
        entityManager.remove(t);
    }

    @Override
    public List<FreePhoneNumber> getPages(Integer size, Integer page) {
        TypedQuery<FreePhoneNumber> q = entityManager.createQuery("select t from FreePhoneNumber t", FreePhoneNumber.class);
        pageCount(page, size, q);
        return q.getResultList();
    }

    @Override
    public Long getPagesCount() {
        TypedQuery<Long> q = entityManager.createQuery("Select count(f) from FreePhoneNumber f", Long.class);
        return q.getSingleResult();
    }
}
