package com.telekom.config;

import com.telekom.dao.api.*;
import com.telekom.mapper.*;
import com.telekom.model.dto.Page;
import com.telekom.model.entity.Contract;
import com.telekom.model.entity.Tariff;
import com.telekom.service.api.JmsService;
import com.telekom.service.api.MailService;
import com.telekom.service.api.PdfCreator;
import com.telekom.service.impl.ContractServiceImpl;
import com.telekom.service.impl.OptionGroupServiceImpl;
import com.telekom.service.impl.OptionServiceImpl;
import com.telekom.service.impl.TariffServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.mock;


@Configuration
public class ContractServiceConfig {
    @Bean
    public ContractServiceImpl contractService() {
        return new ContractServiceImpl();
    }

    @Bean
    public OptionServiceImpl optionService() {
        return mock(OptionServiceImpl.class);
    }

    @Bean
    public OptionGroupServiceImpl optionGroupService() {
         return mock(OptionGroupServiceImpl.class);
    }

    @Bean
    public TariffServiceImpl tariffService() {
        return mock(TariffServiceImpl.class) ;
    }

    @Bean
    public ContractDao contractDao() {
        return mock(ContractDao.class);
    }

    @Bean
    public OptionDao optionDao() {
        return mock(OptionDao.class);
    }

    @Bean
    public TariffDao tariffDao() {
        return mock(TariffDao.class);
    }

    @Bean
    public UserDao userDao() {
        return mock(UserDao.class);
    }

    @Bean
    public ClientDao clientDao() {
        return mock(ClientDao.class);
    }

    @Bean
    public OptionGroupDao optionGroupDao() {
        return mock(OptionGroupDao.class);
    }

    @Bean
    public PhoneNumberDao phoneNumberDao() {
        return mock(PhoneNumberDao.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return mock(PasswordEncoder.class);
    }

    @Bean
    public PdfCreator pdfCreator() {
        return mock(PdfCreator.class);
    }

    @Bean
    public MailService mailService() {
        return mock(MailService.class);
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
    public ClientMapper clientMapper() {
        return mock(ClientMapper.class);
    }

    @Bean
    public Tariff tariff() {
        return mock(Tariff.class);
    }

    @Bean
    public Contract contract() {
        return mock(Contract.class);
    }

    @Bean
    public ContractMapper contractMapper() {
        return mock(ContractMapper.class);
    }

    @Bean
    public OptionGroupMapper optionGroupMapper() {
        return mock(OptionGroupMapper.class);
    }

    @Bean
    public TariffMapper tariffMapper() {
        return mock(TariffMapper.class);
    }

    @Bean
    public OptionMapper optionMapper() {
        return mock(OptionMapper.class);
    }

    @Bean
    public JmsService jmsService() {
        return mock(JmsService.class);
    }
}
