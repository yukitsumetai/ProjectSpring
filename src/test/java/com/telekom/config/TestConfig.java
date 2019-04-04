package com.telekom.config;

import com.telekom.TariffService;
import com.telekom.dao.OptionDao;
import com.telekom.dao.TariffDao;
import com.telekom.dao.TariffDaoImpl2;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.TariffDTO;
import com.telekom.mapper.TariffMapper;
import com.telekom.service.TariffServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public TariffServiceImpl tariffService(){
        return new TariffServiceImpl();
    }
    @Bean
    public TariffDao tariffDao(){
        return mock(TariffDao.class);
    }

    @Bean
    public OptionDao optionDao(){
        return mock(OptionDao.class);
    }

    @Bean
    public TariffMapper tariffMapper(){
        return mock(TariffMapper.class);
    }

    @Bean
    public Tariff Tariff(){
        return mock(Tariff.class);
    }

}