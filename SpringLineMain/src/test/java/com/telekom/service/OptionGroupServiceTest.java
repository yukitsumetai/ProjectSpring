package com.telekom.service;


import com.telekom.config.OptionGroupServiceConfig;
import com.telekom.dao.api.OptionDao;
import com.telekom.dao.api.OptionGroupDao;
import com.telekom.mapper.OptionGroupMapper;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.OptionGroupDto;
import com.telekom.model.dto.Page;
import com.telekom.model.dto.TariffDto;
import com.telekom.model.entity.Option;
import com.telekom.model.entity.OptionGroup;
import com.telekom.model.entity.Tariff;
import com.telekom.service.impl.OptionGroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OptionGroupServiceConfig.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OptionGroupServiceTest {
    @Autowired
    private OptionGroupServiceImpl optionGroupService;
    @Autowired
    private OptionGroupDao optionGroupDao;
    @Autowired
    private OptionDao optionDao;
    @Autowired
    private OptionGroupMapper optionGroupMapper;


    private static Option option;
    private static OptionDto optionDto;
    private static Option option5;
    private static OptionDto option5Dto;

    private static List<Option> options;
    private static List<Option> options5;

    private static List<OptionDto> optionsDto;
    private static List<OptionDto> options5Dto;

    private static OptionGroup og;
    private static OptionGroupDto ogDto;
    private static List<OptionGroup> ogs;
    private static List<OptionGroupDto> ogsDto;

    @BeforeEach
    public void setup() {
        option = new Option();
        option.setId(0);
        option.setValid(true);
        option.setCompatibleTariffs(new HashSet<>());
        Set children = new HashSet();


        option5 = new Option();
        option5.setId(5);
        option5.setValid(false);
        option5.setParent(option);
        option5.setCompatibleTariffs(new HashSet<>());
        children.add(option5);
        option.setChildren(children);

        optionDto = new OptionDto();
        optionDto.setId(0);
        optionDto.setIsValid(true);
        optionDto.setCompatibleTariffs(new HashSet<>());
        Set childrenDto = new HashSet();


        option5Dto = new OptionDto();
        option5Dto.setId(0);
        option5Dto.setIsValid(false);
        option5Dto.setParent(optionDto);
        option5Dto.setCompatibleTariffs(new HashSet<>());

        childrenDto.add(option5Dto);


        options = new ArrayList<>();
        options.add(option);
        options5 = new ArrayList<>();
        options5.add(option5);

        optionsDto = new ArrayList<>();
        optionsDto.add(optionDto);
        options5Dto = new ArrayList<>();
        options5Dto.add(option5Dto);
        optionDto.setChildren(childrenDto);

        og = new OptionGroup();
        og.setId(1);
        og.setValid(true);
        ogDto = new OptionGroupDto();
        ogDto.setId(1);
        ogDto.setIsValid(true);
        ogs = new ArrayList<>();
        ogs.add(og);
        ogsDto = new ArrayList<>();
        ogsDto.add(ogDto);

        when(optionGroupDao.getOne(1)).thenReturn(og);

        when(optionGroupMapper.entityToDto(og)).thenReturn(ogDto);


        when(optionDao.getOne(0)).thenReturn(option);
        when(optionDao.getOne(5)).thenReturn(option5);

    }

    @Test
    public void getAllReturnsAllOptionGroups() {
        when(optionGroupDao.getAll()).thenReturn(ogs);
        assertEquals(ogsDto, optionGroupService.getAll());
    }

    @Test
    public void getAllValidReturnsAllValidOptionGroups() {
        when(optionGroupDao.getAllValid()).thenReturn(ogs);
        assertEquals(ogsDto, optionGroupService.getAllValid());
        assertEquals(true, optionGroupService.getAllValid().get(0).isIsValid());
    }
    /*



    @Test
    public void getAllPromotedReturnsAllValidTatiffs() {
        assertNotEquals(tariffs5Dto, tariffService.getAllValid());
        assertEquals(tariffsDto, tariffService.getAllPromoted());
        assertEquals(true, tariffService.getAllPromoted().get(0).isPromoted());
    }

    @Test
    public void getPageReturnsPage() {
        page = tariffService.getPageDraft(tariffsDto, (long) 5, 1, 1);
        assertEquals(tariffsDto, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(5, page.getTotalPages());
        assertEquals(1, page.getLastPage());
    }

    @Test
    public void getAllPaginatedReturnsAllTatiffsByPage() {
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
    public void getValidPaginatedValidReturnsValidTatiffsByPage() {
        when(tariffDao.getPagesValid(1, 1)).thenReturn(tariffs);
        when(tariffDao.getPagesValid(1, 5)).thenReturn(null);

        page = tariffService.getValidPaginated(1, 1);
        assertEquals(tariffsDto, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(6, page.getTotalPages());
        assertEquals(1, page.getLastPage());

        page = tariffService.getValidPaginated(1, 5);
        assertEquals(true, page.getData().isEmpty());
        assertEquals(5, page.getCurrentPage());
    }

    @Test
    public void getAllPaginatedByOptionValidReturnsValidTatiffsByPageAndOption() {
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
        assertEquals(true, tmp.isExisting());
    }

    @Test
    public void getAllPaginatedByOptionCheckExisting() {
        Set<Tariff> tariffsSet = new HashSet<>();
        tariffsSet.add(tariff);
        option.setCompatibleTariffs(tariffsSet);
        when(tariffDao.getPages(1, 1)).thenReturn(tariffs);

        page = tariffService.getPage(1, 1, 0);
        TariffDto tmp = (TariffDto) page.getData().get(0);

        assertEquals(tariffsDto, page.getData());
        assertEquals(true, tmp.isExisting());
    }

    @Test
    public void getAllPaginatedByOptionCheckNotExisting() {
        when(tariffMapper.entityToDto(tariff5)).thenReturn(tariff5Dto);
        option.setCompatibleTariffs(new HashSet<>());

        when(tariffDao.getPages(1, 5)).thenReturn(tariffs5);

        page = tariffService.getPage(1, 5, 0);
        assertEquals(tariffs5Dto, page.getData());
        TariffDto tmp = (TariffDto) page.getData().get(0);
        assertEquals(false, tmp.isExisting());
    }

    @Test
    public void setOptionsDtoSetsOptionsIfExist() {
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
    public void setOptionsDtoDoNotSetOptionsForNull() {
        option.setCompatibleTariffs(new HashSet<>());
        List<Integer> id = new ArrayList<>();
        id.add(0);

        tariffService.setOptionsDto(tariffDto, null);
        assertNotEquals(null, tariffDto.getOptions());

    }

    @Test
    public void getTariffReturnsTariff() {
        when(tariffDao.getOne(5)).thenReturn(tariff5);
        when(tariffDao.getOne(7)).thenReturn(null);

        assertEquals(tariff5Dto, tariffService.getTariff(5));
        assertEquals(null, tariffService.getTariff(7));
    }


    @Test
    public void addTariffAddsTariffIfOptionsExist() {
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
    public void addTariffAddsTariffNoOptions() {
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
    public void notifyNotNotifiesPromotedNoChanges() {
        tariffService.notify(tariffDto, true);
        tariffService.notify(tariff5Dto, false);
        verify(jmsService, never()).sendMessage();
    }


    @Test
    public void notifyNotNotifiesNotPromoted() {
        assertEquals(false, tariff5Dto.isPromoted());
        tariffService.notify(tariff5Dto);
        verify(jmsService, never()).sendMessage();
    }

    @Test
    public void notifyNotifiesPromotedChangedState() {
        tariffService.notify(tariffDto, false);
        tariffService.notify(tariff5Dto, true);
        tariffService.notifyDeleted();
        tariffService.notify(tariffDto);
        verify(jmsService, times(4)).sendMessage();
    }


    @Test
    public void editTariffEdits() {
        when(tariffDao.getOne(tariffDto.getId())).thenReturn(tariff);
        tariffService.editTariff(tariffDto);
        assertEquals(tariffDto.isIsValid(), tariff.isIsValid());
        assertEquals(tariffDto.isPromoted(), tariff.isPromoted());
        assertEquals(tariffDto.getDescription(), tariff.getDescription());
    }

    @Test
    public void tariffDeletedChangesState() {
        tariffService.deleteTariff(0);
        assertEquals(false, tariff.isIsValid());
        assertEquals(false, tariff.isPromoted());
    }

*/
}
