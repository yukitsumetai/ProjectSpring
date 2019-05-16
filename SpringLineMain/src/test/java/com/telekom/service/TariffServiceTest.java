package com.telekom.service;

import com.telekom.config.TariffServiceConfig;
import com.telekom.dao.api.OptionDao;
import com.telekom.dao.api.TariffDao;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.Page;
import com.telekom.model.dto.TariffDto;
import com.telekom.mapper.TariffMapper;
import com.telekom.model.entity.Option;
import com.telekom.model.entity.Tariff;
import com.telekom.utils.api.JmsService;
import com.telekom.service.impl.TariffServiceImpl;
import org.apache.log4j.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TariffServiceConfig.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TariffServiceTest {
    @Autowired
    TariffServiceImpl tariffService;
    @Autowired
    private TariffDao tariffDao;
    @Autowired
    OptionDao optionDao;
    @Autowired
    private TariffMapper tariffMapper;
    @Autowired
    private JmsService jmsService;
    @Autowired
    private Logger logger;

    private static Tariff tariff;
    private static TariffDto tariffDto;
    private static TariffDto tariff5Dto;
    private static Tariff tariff5;
    private Page page;
    private static List<Tariff> tariffs;
    private static List<Tariff> tariffs5;
    private static List<TariffDto> tariffsDto;
    private static List<TariffDto> tariffs5Dto;
    private static Option option;

    @BeforeAll
    void setup() {
        tariff = new Tariff();
        tariff.setId(0);
        tariff.setIsValid(true);
        tariff.setPromoted(true);

        tariff5 = new Tariff();
        tariff5.setId(5);
        tariff5.setIsValid(false);

        tariffDto = new TariffDto();
        tariffDto.setId(0);
        tariffDto.setIsValid(true);
        tariffDto.setPromoted(true);

        tariff5Dto = new TariffDto();
        tariff5Dto.setId(5);
        tariff5Dto.setIsValid(false);

        tariffs = new ArrayList<>();
        tariffs.add(tariff);
        tariffs5 = new ArrayList<>();
        tariffs5.add(tariff5);

        tariffsDto = new ArrayList<>();
        tariffsDto.add(tariffDto);
        tariffs5Dto = new ArrayList<>();
        tariffs5Dto.add(tariff5Dto);

        option = new Option();
        option.setId(0);

        when(tariffDao.getAllPromoted()).thenReturn(tariffs);
        when(tariffDao.getAllValid()).thenReturn(tariffs);
        when(tariffMapper.entityToDto(tariff, false)).thenReturn(tariffDto);
        when(tariffMapper.entityToDto(tariff5, false)).thenReturn(tariff5Dto);
        when(tariffDao.getPagesCount()).thenReturn((long) 6);
        when(tariffDao.getPagesValidCount()).thenReturn((long) 6);


        when(optionDao.getOne(0)).thenReturn(option);
    }

    @Test
    void getAllValidReturnsAllValidTatiffs() {
        assertEquals(tariffsDto, tariffService.getAllValid());
        assertNotEquals(tariffs5Dto, tariffService.getAllValid());
        assertTrue(tariffService.getAllValid().get(0).isIsValid());
    }


    @Test
    void getAllPromotedReturnsAllValidTatiffs() {
        assertNotEquals(tariffs5Dto, tariffService.getAllValid());
        assertEquals(tariffsDto, tariffService.getAllPromoted());
        assertTrue(tariffService.getAllPromoted().get(0).isPromoted());
    }

    @Test
    void getPageReturnsPage() {
        page = tariffService.getPageDraft(tariffsDto, (long) 5, 1, 1);
        assertEquals(tariffsDto, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(5, page.getTotalPages());
        assertEquals(1, page.getLastPage());
    }

    @Test
    void getAllPaginatedReturnsAllTatiffsByPage() {
        when(tariffDao.getPages(1, 1)).thenReturn(tariffs);
        when(tariffDao.getPages(1, 5)).thenReturn(tariffs5);
        when(tariffDao.getPages(1, 7)).thenReturn(null);

        page = tariffService.getPage(1, 1);
        assertEquals(tariffsDto, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(6, page.getTotalPages());
        assertEquals(1, page.getLastPage());

        page = tariffService.getPage(1, 5);
        assertEquals(tariffs5Dto, page.getData());
        assertEquals(5, page.getCurrentPage());

        page = tariffService.getPage(1, 7);
        //assertEquals(true, page.getData().isEmpty());
        assertEquals(7, page.getCurrentPage());
    }

    @Test
    void getValidPaginatedValidReturnsValidTatiffsByPage() {
        when(tariffDao.getPagesValid(1, 1)).thenReturn(tariffs);
        when(tariffDao.getPagesValid(1, 5)).thenReturn(null);

        page = tariffService.getValidPaginated(1, 1);
        assertEquals(tariffsDto, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(6, page.getTotalPages());
        assertEquals(1, page.getLastPage());

        page = tariffService.getValidPaginated(1, 5);
        assertTrue(page.getData().isEmpty());
        assertEquals(5, page.getCurrentPage());
    }

    @Test
    void getAllPaginatedByOptionValidReturnsValidTatiffsByPageAndOption() {
        Set<Tariff> tariffsSet = new HashSet<>();
        tariffsSet.add(tariff);
        option.setCompatibleTariffs(tariffsSet);

        when(tariffDao.getPages(1, 1)).thenReturn(tariffs);
        page = tariffService.getPage(1, 1, 0);
        TariffDto tmp = (TariffDto) page.getData().get(0);

        assertEquals(tariffsDto, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(6, page.getTotalPages());
        assertEquals(1, page.getLastPage());
        assertTrue(tmp.isExisting());
    }

    @Test
    void getAllPaginatedByOptionCheckExisting() {
        Set<Tariff> tariffsSet = new HashSet<>();
        tariffsSet.add(tariff);
        option.setCompatibleTariffs(tariffsSet);
        when(tariffDao.getPages(1, 1)).thenReturn(tariffs);

        page = tariffService.getPage(1, 1, 0);
        TariffDto tmp = (TariffDto) page.getData().get(0);

        assertEquals(tariffsDto, page.getData());
        assertTrue(tmp.isExisting());
    }

    @Test
    void getAllPaginatedByOptionCheckNotExisting() {
        when(tariffMapper.entityToDto(tariff5, false)).thenReturn(tariff5Dto);
        option.setCompatibleTariffs(new HashSet<>());

        when(tariffDao.getPages(1, 5)).thenReturn(tariffs5);

        page = tariffService.getPage(1, 5, 0);
        assertEquals(tariffs5Dto, page.getData());
        TariffDto tmp = (TariffDto) page.getData().get(0);
        assertFalse(tmp.isExisting());
    }

    @Test
    void setOptionsDtoSetsOptionsIfExist() {
        option.setCompatibleTariffs(new HashSet<>());
        List<Integer> id = new ArrayList<>();
        id.add(0);

        tariffService.setOptionsDto(tariffDto, id);
        Set<OptionDto> tmp = tariffDto.getOptions();
        for (OptionDto o : tmp) {
            assertEquals(0, o.getId());
            assertNotEquals(1, o.getId());
        }

    }

    @Test
    void setOptionsDtoDoNotSetOptionsForNull() {
        option.setCompatibleTariffs(new HashSet<>());
        List<Integer> id = new ArrayList<>();
        id.add(0);

        tariffService.setOptionsDto(tariffDto, null);
        assertNotEquals(null, tariffDto.getOptions());

    }

    @Test
    void getTariffReturnsTariff() {
        when(tariffDao.getOne(5)).thenReturn(tariff5);
        when(tariffDao.getOne(7)).thenReturn(null);

        assertEquals(tariff5Dto, tariffService.getTariff(5));
        assertNull(tariffService.getTariff(7));
    }


    @Test
    void addTariffAddsTariffIfOptionsExist() {
        tariff.setOptions(new HashSet<>());
        Set<Option> tmp2 = tariff.getOptions();
        for (Option o : tmp2) {
            assertNotEquals(0, o.getId());
            assertNotEquals(1, o.getId());
        }
        tariffDto.setOptions(new HashSet<>());
        Set<OptionDto> tmp3 = tariffDto.getOptions();
        for (OptionDto o : tmp3) {
            assertNotEquals(0, o.getId());
            assertNotEquals(1, o.getId());
        }

        when(tariffMapper.dtoToEntity(tariffDto)).thenReturn(tariff);

        OptionDto optionDto = new OptionDto();
        optionDto.setId(0);
        Set<OptionDto> optionsSet = new HashSet<>();
        optionsSet.add(optionDto);
        tariffDto.setOptions(optionsSet);
        tariffService.add(tariffDto);
        tmp2 = tariff.getOptions();
        for (Option o : tmp2) {
            assertEquals(0, o.getId());
            assertNotEquals(1, o.getId());
        }
    }

    @Test
    void addTariffAddsTariffNoOptions() {
        tariff.setOptions(new HashSet<>());
        Set<Option> tmp2 = tariff.getOptions();
        for (Option o : tmp2) {
            assertNotEquals(0, o.getId());
            assertNotEquals(1, o.getId());
        }

        assertNotEquals(null, tariffDto.getOptions());
        tariffService.add(tariffDto);
        assertNotEquals(null,  tariff.getOptions());
    }


    @Test
    void notifyNotNotifiesPromotedNoChanges() {
        tariffService.notify(tariffDto, true);
        tariffService.notify(tariff5Dto, false);
        verify(jmsService, never()).sendMessage();
    }


    @Test
    void notifyNotNotifiesNotPromoted() {
        assertFalse(tariff5Dto.isPromoted());
        tariffService.notify(tariff5Dto);
        verify(jmsService, never()).sendMessage();
    }

    @Test
    void notifyNotifiesPromotedChangedState() {
        tariffService.notify(tariffDto, false);
        tariffService.notify(tariff5Dto, true);
        tariffService.notifyDeleted();
        tariffService.notify(tariffDto);
        verify(jmsService, times(4)).sendMessage();
    }


    @Test
    void editTariffEdits() {
        when(tariffDao.getOne(tariffDto.getId())).thenReturn(tariff);
        tariffService.editTariff(tariffDto);
        assertEquals(tariffDto.isIsValid(), tariff.isIsValid());
        assertEquals(tariffDto.isPromoted(), tariff.isPromoted());
        assertEquals(tariffDto.getDescription(), tariff.getDescription());
    }

    @Test
    void tariffDeletedChangesState() {
        tariffService.deleteTariff(0);
        assertFalse(tariff.isIsValid());
        assertFalse(tariff.isPromoted());
    }


}
