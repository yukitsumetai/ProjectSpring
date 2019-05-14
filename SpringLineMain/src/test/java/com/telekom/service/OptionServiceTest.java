package com.telekom.service;

import com.telekom.config.OptionServiceConfig;
import com.telekom.dao.api.OptionDao;
import com.telekom.dao.api.OptionGroupDao;
import com.telekom.dao.api.TariffDao;
import com.telekom.mapper.OptionMapper;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.OptionGroupDto;
import com.telekom.model.dto.Page;
import com.telekom.model.dto.TariffDto;
import com.telekom.model.entity.Option;
import com.telekom.model.entity.OptionGroup;
import com.telekom.model.entity.Tariff;
import com.telekom.service.impl.OptionServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OptionServiceConfig.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OptionServiceTest {
    @Autowired
    OptionServiceImpl optionService;
    @Autowired
    private OptionDao optionDao;
    @Autowired
    private OptionGroupDao optionGroupDao;
    @Autowired
    private TariffDao tariffDao;
    @Autowired
    private OptionMapper optionMapper;


    private static Option option;
    private static OptionDto optionDto;
    private static Option option5;
    private static OptionDto option5Dto;
    private static Option option7;
    private static OptionDto option7Dto;

    private Page page;
    private static List<Option> options;
    private static List<Option> options5;
    private static List<Option> options7;
    private static List<OptionDto> optionsDto;
    private static List<OptionDto> options5Dto;
    private static List<OptionDto> options7Dto;

    private static Tariff tariff;
    private static TariffDto tariffDto;
    private static OptionGroup og;
    private static OptionGroupDto ogDto;

    @BeforeEach
    void setup() {
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

        option7 = new Option();
        option7.setId(7);
        option7.setCompatibleTariffs(new HashSet<>());
        options7 = new ArrayList<>();
        options7.add(option7);


        option7Dto = new OptionDto();
        option7Dto.setId(7);
        option7Dto.setCompatibleTariffs(new HashSet<>());
        options7Dto = new ArrayList<>();
        options7Dto.add(option7Dto);

        optionDto.setChildren(childrenDto);

        tariff = new Tariff();
        tariff.setId(0);
        tariff.setOptions(new HashSet<>());


        tariffDto = new TariffDto();
        tariffDto.setId(0);
        tariffDto.setOptions(new HashSet<>());

        og = new OptionGroup();
        og.setId(1);
        ogDto = new OptionGroupDto();
        ogDto.setId(1);

        when(optionGroupDao.getOne(1)).thenReturn(og);


        when(optionMapper.entityToDto(option)).thenReturn(optionDto);
        when(optionMapper.entityToDto(option5)).thenReturn(option5Dto);
        when(optionMapper.entityToDto(option7)).thenReturn(option7Dto);

        when(optionDao.getPagesCount()).thenReturn((long) 6);
        when(tariffDao.getOne(0)).thenReturn(tariff);

        when(optionDao.getOne(0)).thenReturn(option);
        when(optionDao.getOne(5)).thenReturn(option5);
        when(optionDao.getOne(7)).thenReturn(option7);
    }

    @Test
    void getAllPaginatedReturnsAllOptionsPaginated() {
        when(optionDao.getPages(1, 1)).thenReturn(options);
        when(optionDao.getPages(1, 5)).thenReturn(options5);
        when(optionDao.getPages(1, 7)).thenReturn(null);

        page = optionService.getPage(1, 1);
        assertEquals(optionsDto, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(6, page.getTotalPages());
        assertEquals(1, page.getLastPage());

        page = optionService.getPage(1, 5);
        assertEquals(options5Dto, page.getData());
        assertEquals(5, page.getCurrentPage());

        page = optionService.getPage(1, 7);
        assertTrue(page.getData().isEmpty());
        assertEquals(7, page.getCurrentPage());
    }

    @Test
    void getOptionsForTariffReturnsOptionsByPageAndTariff() {
        Set<Option> optionsSet = new HashSet<>();
        optionsSet.add(option);
        tariff.setOptions(optionsSet);

        when(optionDao.getPages(1, 1)).thenReturn(options);
        page = optionService.getOptionsForTariff(1, 1, 0);
        OptionDto tmp = (OptionDto) page.getData().get(0);

        assertEquals(optionsDto, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(6, page.getTotalPages());
        assertEquals(1, page.getLastPage());
        assertTrue(tmp.isExisting());
    }

    @Test
    void getOptionsForTariffReturnsOptionsByPageAndTariffNotExisting() {
        option.setCompatibleTariffs(new HashSet<>());
        when(optionDao.getPages(1, 5)).thenReturn(options5);
        page = optionService.getOptionsForTariff(1, 5, 0);
        assertEquals(options5Dto, page.getData());
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertFalse(tmp.isExisting());
    }

    @Test
    void getOptionsForNewOptionParentReturnsOptionsByPageAndOption() {
        when(optionDao.getAllNoParent(1, 1)).thenReturn(options);
        when(optionDao.getPagesCountNoParent()).thenReturn((long) 5);

        page = optionService.getOptionsForNewOption(1, 1, true);
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertEquals(optionsDto, page.getData());
        assertEquals(5, page.getTotalPages());
        assertNull(tmp.getParent());
    }

    @Test
    void getOptionsForNewOptionChildrenReturnsOptionsByPageAndOption() {
        when(optionMapper.entityToDto(option7)).thenReturn(option7Dto);

        when(optionDao.getAllNoChildrenAndParent(1, 7)).thenReturn(options7);
        when(optionDao.getPagesCountNoParentNoChildren()).thenReturn((long) 3);

        page = optionService.getOptionsForNewOption(1, 7, false);
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertEquals(options7Dto, page.getData());
        assertEquals(3, page.getTotalPages());
        assertNull(tmp.getParent());
        assertTrue(tmp.getChildren().isEmpty());
    }

    @Test
    void getOptionsForExistingOptionParentReturnsOptionsByPageAndOption() {

        when(optionDao.getAllNoParent(1, 1)).thenReturn(options);
        when(optionDao.getPagesCountNoParent()).thenReturn((long) 5);
        page = optionService.getPageForExisting(1, 1, true, 5);
        OptionDto tmp = (OptionDto) page.getData().get(0);

        assertEquals(optionsDto, page.getData());
        assertEquals(5, page.getTotalPages());
        assertTrue(tmp.isExisting());
    }

    @Test
    void getOptionsForExistingOptionParentReturnsOptionsByPageAndOptionNoExisting() {

        when(optionDao.getAllNoParent(1, 7)).thenReturn(options7);
        when(optionDao.getPagesCountNoParent()).thenReturn((long) 5);
        page = optionService.getPageForExisting(1, 7, true, 0);
        OptionDto tmp = (OptionDto) page.getData().get(0);

        assertEquals(options7Dto, page.getData());
        assertEquals(5, page.getTotalPages());
        assertFalse(tmp.isExisting());
    }


    @Test
    void getOptionsForExistingOptionChildrenReturnsOptionsByPageAndOption() {

        when(optionDao.getAllNoChildrenAndParentExisting(1, 5, 0)).thenReturn(options5);
        when(optionDao.getPagesNoChildrenAndParentExisting(5)).thenReturn((long) 3);

        page = optionService.getPageForExisting(1, 5, false, 0);
        OptionDto tmp = (OptionDto) page.getData().get(0);

        assertEquals(options5Dto, page.getData());
        assertEquals(3, page.getTotalPages());
        assertTrue(tmp.isExisting());
    }

    @Test
    void getOptionsForExistingOptionChildrenReturnsOptionsByPageAndOptionNoExisting() {

        when(optionDao.getAllNoChildrenAndParentExisting(1, 1, 5)).thenReturn(options7);
        when(optionDao.getPagesNoChildrenAndParentExisting(5)).thenReturn((long) 3);
        page = optionService.getPageForExisting(1, 1, false, 5);
        OptionDto tmp = (OptionDto) page.getData().get(0);

        assertEquals(options7Dto, page.getData());
        assertEquals(3, page.getTotalPages());
        assertFalse(tmp.isExisting());
    }

    @Test
    void getOptionsForOptionGroupsReturnsOptionsByOG() {
        when(optionDao.getAllNoParentNoGroup(1, 1)).thenReturn(options);
        when(optionDao.getPagesCountNoParentNoGroup()).thenReturn((long) 2);

        page = optionService.getOpionsForOptionGroup(1, 1);
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertEquals(optionsDto, page.getData());
        assertEquals(2, page.getTotalPages());
        assertNull(tmp.getParent());
        assertNull(tmp.getOptionGroup());
    }

    @Test
    void getOptionsForExistingOptionGroupsReturnsOptionsByOG() {

        option7.setGroup(og);
        option7Dto.setOptionGroup(ogDto);


        when(optionDao.getAllNoParentNoGroupExisting(1, 1, og.getId())).thenReturn(options7);
        when(optionDao.getPagesCountNoParentNoGroupExisting(og.getId())).thenReturn((long) 8);

        page = optionService.getOptionsForExistingOptionGroup(1, 1, og.getId());
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertEquals(options7Dto, page.getData());
        assertEquals(8, page.getTotalPages());
        assertEquals(ogDto, tmp.getOptionGroup());

    }

    @Test
    void getOptionsForExistingOptionGroupsReturnsOptionsByOGNoExisting() {
        OptionGroup og = new OptionGroup();
        og.setId(0);
        OptionGroupDto ogDto = new OptionGroupDto();
        ogDto.setId(0);

        when(optionGroupDao.getOne(og.getId())).thenReturn(og);
        when(optionDao.getAllNoParentNoGroupExisting(1, 1, og.getId())).thenReturn(options5);
        when(optionDao.getPagesCountNoParentNoGroupExisting(og.getId())).thenReturn((long) 8);

        page = optionService.getOptionsForExistingOptionGroup(1, 1, og.getId());
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertEquals(options5Dto, page.getData());
        assertEquals(8, page.getTotalPages());
        assertNull(tmp.getOptionGroup());
        assertNotEquals(ogDto, tmp.getOptionGroup());
    }


    @Test
    void setCompatibleTariffsSetsTariffs() {
        List<Integer> id = new ArrayList<>();
        id.add(0);
        optionService.setCompatibleTariffs(optionDto, id);
        Set<TariffDto> tmp = optionDto.getCompatibleTariffs();
        for (TariffDto o : tmp) {
            assertEquals(0, o.getId());
            assertNotEquals(1, o.getId());
        }

    }


    @Test
    void setCompatibleTariffsNoTariffsWithNull() {
        optionService.setCompatibleTariffs(optionDto, null);
        assertTrue(optionDto.getCompatibleTariffs().isEmpty());
    }


    @Test
    void setChildrenSetsChildren() {
        List<Integer> id = new ArrayList<>();
        id.add(7);
        optionService.setChildren(optionDto, id);
        Set<OptionDto> tmp = optionDto.getChildren();
        for (OptionDto o : tmp) {
            assertEquals(7, o.getId());
            assertNotEquals(5, o.getId());
        }
    }


    @Test
    void setChildrenNoChildrenWithNull() {
        optionService.setChildren(optionDto, null);
        assertFalse(optionDto.getChildren().isEmpty());
        optionService.setChildren(option7Dto, null);
        assertTrue(option7Dto.getChildren().isEmpty());
    }


    @Test
    void setOptionGroupSetsOptionGroup() {
        optionService.setOptionGroup(optionDto, 1);
        assertEquals(1, optionDto.getOptionGroup().getId());
        assertNotEquals(5, optionDto.getOptionGroup().getId());
    }


    @Test
    void setOptionGroupNoOptionGroupWithNull() {
        optionService.setOptionGroup(optionDto, null);
        assertNull(optionDto.getOptionGroup());
        optionService.setOptionGroup(optionDto, 0);
        assertNull(optionDto.getOptionGroup());
    }

    @Test
    void setParentSetsParent() {
        optionService.setParent(option7Dto, 0);
        assertEquals(0, option7Dto.getParent().getId());
        assertNotEquals(5, option7Dto.getParent().getId());
    }


    @Test
    void setParentNoParentWithNull() {
        optionService.setParent(option7Dto, null);
        assertNull(option7Dto.getParent());
    }

    @Test
    void findByTariffReturnsParentOptionsByTariff() {
        when(optionDao.findByTariffParents(0)).thenReturn(options7);
        assertIterableEquals(options7Dto, optionService.findByTariff(0));
        assertNotEquals(null, optionService.findByTariff(0));
    }

    @Test
    void findByTariffChildrenReturnsChildrenOptionsByTariff() {
        when(optionDao.findByTariffChildren(0)).thenReturn(options5);
        assertIterableEquals(options5Dto, optionService.findByTariffChildren(0));
        assertNotEquals(null, optionService.findByTariff(0));
    }

    @Test
    void updateChildrenUpdatesChildrenWhenExistCurrentEmpty() {

        option.setChildren(new HashSet<>());
        option5.setParent(null);
        optionService.updateChildren(optionDto, option);
        assertEquals(0, option7.getParent().getId());
        assertNull(option5.getParent());
        Set<Option> tmp = option.getChildren();
        for (Option o : tmp) {
            assertEquals(7, o.getId());
        }
    }

    @Test
    void updateChildrenUpdatesChildrenWhenExistCurrent() {
        OptionGroup og7 = new OptionGroup();
        option7.setGroup(og7);

        assertEquals(0, option5.getParent().getId());
        Set<OptionDto> tmp = new HashSet<>();
        tmp.add(option7Dto);
        optionDto.setChildren(tmp);

        optionService.updateChildren(optionDto, option);
        assertEquals(0, option7.getParent().getId());
        assertNull(option7.getGroup());
        assertNull(option5.getParent());


    }

    @Test
    void updateChildrenNoUpdatesCurrentEmpty() {
        OptionDto od = new OptionDto();
        od.setChildren(new HashSet<>());
        optionService.updateChildren(od, option7);
        assertTrue(option7.getChildren().isEmpty());
    }

    @Test
    void updateChildrenNoUpdatesCurrentNotEmpty() {
        OptionDto od = new OptionDto();
        od.setChildren(new HashSet<>());
        assertEquals(option, option5.getParent());
        optionService.updateChildren(od, option);
        assertNull(option5.getParent());
    }

    @Test
    void updateChildrenNoUpdatesForChildren() {
        OptionDto od = new OptionDto();
        Set ods = new HashSet();
        ods.add(option7Dto);
        od.setChildren(ods);
        optionService.updateChildren(od, option5);
        assertTrue(option5.getChildren().isEmpty());
    }

    @Test
    void updateTariffsUpdatesTariffWhenExistCurrentNotEmpty() {
        TariffDto tariffDto1 = new TariffDto();
        tariffDto1.setId(1);
        tariffDto1.setOptions(new HashSet<>());
        Tariff tariff1 = new Tariff();
        tariff1.setId(1);
        tariff1.setOptions(new HashSet<>());
        when(tariffDao.getOne(1)).thenReturn(tariff1);

        Set<TariffDto> tds = new HashSet<>();
        tds.add(tariffDto1);
        optionDto.setCompatibleTariffs(tds);


        Set<Tariff> tds2 = new HashSet<>();
        tds2.add(tariff);
        option.setCompatibleTariffs(tds2);

        Set<Option> ods2 = new HashSet<>();
        ods2.add(option);
        tariff.setOptions(ods2);
        optionService.updateTariff(optionDto, option);

        Set<Option> tmp2 = tariff1.getOptions();
        for (Option o : tmp2) {
            assertEquals(0, o.getId());
        }
        assertTrue(tariff.getOptions().isEmpty());
    }

    @Test
    void updateTariffsUpdatesTariffWhenExistCurrentEmpty() {

        Set<TariffDto> tds = new HashSet<>();
        tds.add(tariffDto);
        optionDto.setCompatibleTariffs(tds);

        Set<Option> tmp2 = tariff.getOptions();
        for (Option o : tmp2) {
            assertEquals(0, o.getId());
        }
    }

    @Test
    void updateTariffsNoUpdatesCurrentNotEmpty() {
        Set<Tariff> tds2 = new HashSet<>();
        tds2.add(tariff);
        option.setCompatibleTariffs(tds2);
        Set<Option> ods2 = new HashSet<>();
        ods2.add(option);
        tariff.setOptions(ods2);
        optionService.updateTariff(optionDto, option);
        Set<Option> tmp = tariff.getOptions();
        assertTrue(tmp.isEmpty());
    }

    @Test
    void updateTariffsNoUpdatesCurrentEmpty() {
        optionService.updateTariff(optionDto, option);
        assertTrue(option.getCompatibleTariffs().isEmpty());
    }

    @Test
    void updateOptionGroupUpdatesGroup() {
        optionDto.setOptionGroup(ogDto);
        optionService.updateOptionGroup(optionDto, option);
        assertEquals(og, option.getGroup());
    }

    @Test
    void updateOptionGroupNoUpdates() {

        optionService.updateOptionGroup(optionDto, option);
        assertNull(option.getGroup());
//dummy group
        OptionGroup og0 = new OptionGroup();
        og0.setId(0);
        OptionGroupDto og0Dto = new OptionGroupDto();
        og0Dto.setId(0);
        optionDto.setOptionGroup(og0Dto);
        when(optionGroupDao.getOne(0)).thenReturn(og0);
        assertNull(option.getGroup());
//child option
        option5Dto.setOptionGroup(ogDto);
        optionService.updateOptionGroup(option5Dto, option5);
        assertNull(option5.getGroup());
    }

    @Test
    void updateOptionParentUpdatesParent() {
        assertEquals(option, option5.getParent());
        option5Dto.setParent(option7Dto);
        optionService.updateOptionParent(option5Dto, option5);
        assertIterableEquals(options5, option.getChildren());
        assertNotEquals(option, option5.getParent());
    }

    @Test
    void updateOptionParentNoUpdates() {
//no update
        optionService.updateOptionParent(option5Dto, option5);
        assertEquals(option, option5.getParent());

        //no group
        optionService.updateOptionParent(option7Dto, option7);
        assertNull(option.getParent());

        //parent itself
        optionDto.setParent(optionDto);
        assertNull(option.getParent());
        //parent is child
        option7Dto.setParent(option5Dto);
        assertNull(option7.getParent());
    }

    @Test
    void addAddsOption() {
        when(optionMapper.dtoToEntity(optionDto)).thenReturn(option);
        optionService.add(optionDto);
        verify(optionDao).add(option);
    }

    @Test
    void getOneReturnsOption() {
        assertEquals(optionDto, optionService.getOne(0));
        assertEquals(option5Dto, optionService.getOne(5));
    }

    @Test
    void getOneReturnsNullForNotFound() {
        when(optionDao.getOne(12)).thenReturn(null);
        assertNull(optionService.getOne(12));
    }


    @Test
    void editOptionEditsOption() {
        when(optionMapper.dtoToEntity(optionDto)).thenReturn(option);
        optionDto.setIsValid(false);
        optionDto.setOptionGroup(ogDto);
        optionDto.setDescription("test");
        optionDto.setChildren(new HashSet<>());
        optionDto.setParent(option5Dto);
        Set<TariffDto> tds = new HashSet<>();
        tds.add(tariffDto);
        optionDto.setCompatibleTariffs(tds);

        optionService.editOption(optionDto);
        assertFalse(option.isValid());
        assertEquals("test", option.getDescription());
        assertEquals(og, option.getGroup());
        Set<Option> tmp = option5.getChildren();
        for (Option o : tmp) {
            assertEquals(0, o.getId());
        }
        assertNull(option5.getParent());
        Set<Tariff> tmp2 = option.getCompatibleTariffs();
        for (Tariff o : tmp2) {
            assertEquals(0, o.getId());
        }

    }

    @Test
    void removeRelationsRemovesChildren() {
        assertNotEquals(true, optionDto.getChildren().isEmpty());
        optionService.removeRelations(optionDto);
        assertTrue(optionDto.getChildren().isEmpty());
    }

    @Test
    void removeRelationsRemovesParent() {
        assertNotEquals(null, option5Dto.getParent());
        optionService.removeRelations(optionDto);
        assertTrue(optionDto.getChildren().isEmpty());
    }

    @Test
    void deleteOptionDeletesOption() {
        assertNotEquals(false, optionDto.isIsValid());
        optionService.deleteOption(0);
        assertFalse(option.isValid());
    }

}


