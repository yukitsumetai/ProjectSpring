
package com.telekom.repository;

import com.telekom.entity.Client;
import com.telekom.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {

    Tariff findByName(String name);

}
