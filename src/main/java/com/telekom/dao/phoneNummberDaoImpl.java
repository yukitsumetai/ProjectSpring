package com.telekom.dao;

import com.telekom.entity.PhoneNumber;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
@Component
@Repository
public class phoneNummberDaoImpl implements PhoneNumberDao {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public void deleteNumber(BigInteger phoneNumber) {
        TypedQuery<PhoneNumber> q = entityManager.createQuery(
                "select t from PhoneNumber t where t.phoneNumber=:phoneNumber", PhoneNumber.class
        );
        q.setParameter("phoneNumber", phoneNumber);
        PhoneNumber t=q.getResultList().stream().findAny().orElse(null);
        entityManager.remove(t);
    }

    @Override
    public List<PhoneNumber> getAll() {
        return entityManager.createQuery("select t from PhoneNumber t").getResultList();
    }
}
