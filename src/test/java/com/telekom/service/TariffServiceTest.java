package com.telekom.service;

import com.telekom.config.TestConfig;
import com.telekom.dao.TariffDao;
import com.telekom.entityDTO.TariffDTO;
import com.telekom.mapper.TariffMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
class TariffServiceTest {
    @Autowired
    TariffServiceImpl tariffService;
    @Autowired
    TariffDao tariffDao;

    @Autowired
    TariffMapper tariffMapper;

    private static final Integer id = 110004;
    private static TariffDTO tariff;

    @Test
    public void returnTariffById() {
       tariffService.getOne(id);
        verify(tariffDao).getOne(id);
    }

    @Test
    public void returnAllTariffs() {
        tariffService.getAll();
        verify(tariffDao).getAll();
    }

    @Test
    public void returnAllValidTariffs() {
        tariffService.getAllValid();
        verify(tariffDao).getAllValid();
    }


    @Test
    public void tariffDeleted(){
       // tariffService.deleteTariff(id);
        TariffDTO t2 =  tariffService.getOne(id);
        //assertEquals(t2.isIsValid(), false);
    }



}
