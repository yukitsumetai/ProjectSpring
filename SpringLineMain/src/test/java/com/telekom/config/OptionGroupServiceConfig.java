package com.telekom.config;

import com.telekom.dao.api.OptionDao;
import com.telekom.dao.api.OptionGroupDao;
import com.telekom.mapper.OptionGroupMapper;
import com.telekom.service.impl.OptionGroupServiceImpl;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

public class OptionGroupServiceConfig {
    @Bean
    public OptionGroupServiceImpl optionGroupService() {
        return new OptionGroupServiceImpl();
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
    public OptionGroupMapper optionMapper() {
        return mock(OptionGroupMapper.class);
    }


}