package com.telekom.config;

import com.telekom.dao.api.ClientDao;
import com.telekom.mapper.ClientMapper;
import com.telekom.model.dto.Page;
import com.telekom.service.impl.ClientServiceImpl;
import com.telekom.service.impl.ImageRecognitionImpl;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

public class ClientServiceConfig {

    @Bean
    public ClientServiceImpl clientService() {
        return new ClientServiceImpl();
    }
    @Bean
    public Logger logger() {
        return mock(Logger.class);
    }
    @Bean
    public ClientDao clientDao() {
        return mock(ClientDao.class);
    }

    @Bean
    public Page page() {
        return mock(Page.class);
    }

    @Bean
    public ClientMapper clientMapper() {
        return mock(ClientMapper.class);
    }

    @Bean
    public ImageRecognitionImpl imageRecognitionImpl() {
        return mock(ImageRecognitionImpl.class);
    }
}
