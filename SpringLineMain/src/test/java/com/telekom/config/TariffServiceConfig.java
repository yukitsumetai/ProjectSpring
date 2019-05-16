package com.telekom.config;

import com.telekom.dao.api.OptionDao;
import com.telekom.dao.api.TariffDao;
import com.telekom.model.dto.Page;
import com.telekom.model.entity.Tariff;
import com.telekom.mapper.TariffMapper;
import com.telekom.utils.api.JmsService;
import com.telekom.service.impl.TariffServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TariffServiceConfig {

    @Bean
    public TariffServiceImpl tariffService() {
        return new TariffServiceImpl();
    }

    @Bean
    public TariffDao tariffDao() {
        return mock(TariffDao.class);
    }

    @Bean
    public JmsService jmsService() {
        return mock(JmsService.class);
    }

    @Bean
    public Page page() {
        return mock(Page.class);
    }

    @Bean
    public OptionDao optionDao() {
        return mock(OptionDao.class);
    }

    @Bean
    public TariffMapper tariffMapper() {
        return mock(TariffMapper.class);
    }

    @Bean
    public Tariff Tariff() {
        return mock(Tariff.class);
    }


}
