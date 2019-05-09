package com.telekom.config;

import com.telekom.dao.api.PhoneNumberDao;
import com.telekom.model.dto.Page;
import com.telekom.service.impl.PhoneNumberServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class PhoneNumberServiceConfig {
    @Bean
    public PhoneNumberServiceImpl tariffService() {
        return new PhoneNumberServiceImpl();
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
    public PhoneNumberDao phoneNumberDao() {
        return mock(PhoneNumberDao.class);
    }
}
