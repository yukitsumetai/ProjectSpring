package com.telekom.config;


import com.telekom.dao.api.OptionDao;
import com.telekom.dao.api.OptionGroupDao;
import com.telekom.dao.api.TariffDao;
import com.telekom.mapper.OptionMapper;
import com.telekom.mapper.TariffMapper;
import com.telekom.model.dto.Page;
import com.telekom.model.entity.Tariff;
import com.telekom.service.impl.OptionServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class OptionServiceConfig {

    @Bean
    public OptionServiceImpl optionService() {
        return new OptionServiceImpl();
    }

    @Bean
    public OptionDao optionDao() {
        return mock(OptionDao.class);
    }

    @Bean
    public OptionGroupDao optionGroupDao() {
        return mock(OptionGroupDao.class);
    }

    @Bean
    public TariffDao tariffDao() {
        return mock(TariffDao.class);
    }

    @Bean
    public Logger logger() {
        return mock(Logger.class);
    }

    @Bean
    public Page page() {
        return mock(Page.class);
    }

    @Bean
    public OptionMapper optionMapper() {
        return mock(OptionMapper.class);
    }

    @Bean
    public Tariff Tariff() {
        return mock(Tariff.class);
    }

}
