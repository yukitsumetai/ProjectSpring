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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    @Autowired
    private Logger logger;

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

    @BeforeAll
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
        childrenDto.add(option7Dto);
        optionDto.setChildren(childrenDto);

        tariff = new Tariff();
        tariff.setId(0);
        tariff.setOptions(new HashSet<>());


        TariffDto tariffDto = new TariffDto();
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

        // when(optionDao.getAllPromoted()).thenReturn(options);
        //when(tariffDao.getAllValid()).thenReturn(tariffs);

        when(optionDao.getPagesCount()).thenReturn((long) 6);
        // when(tariffDao.getPagesValidCount()).thenReturn((long) 6);


        when(tariffDao.getOne(0)).thenReturn(tariff);

        when(optionDao.getOne(0)).thenReturn(option);
        when(optionDao.getOne(5)).thenReturn(option5);
        when(optionDao.getOne(7)).thenReturn(option7);
    }

    @Test
    public void getAllPaginatedReturnsAllOptionsPaginated() {
        when(optionDao.getPages(1, 1)).thenReturn(options);
        when(optionDao.getPages(1, 5)).thenReturn(options5);
        when(optionDao.getPages(1, 7)).thenReturn(null);

        page = optionService.getOptions(1, 1);
        assertEquals(optionsDto, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(6, page.getTotalPages());
        assertEquals(1, page.getLastPage());

        page = optionService.getOptions(1, 5);
        assertEquals(options5Dto, page.getData());
        assertEquals(5, page.getCurrentPage());

        page = optionService.getOptions(1, 7);
        assertEquals(true, page.getData().isEmpty());
        assertEquals(7, page.getCurrentPage());
    }

    @Test
    public void getOptionsForTariffReturnsOptionsByPageAndTariff() {
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
        assertEquals(true, tmp.isExisting());
    }

    @Test
    public void getOptionsForTariffReturnsOptionsByPageAndTariffNotExisting() {
        option.setCompatibleTariffs(new HashSet<>());
        when(optionDao.getPages(1, 5)).thenReturn(options5);
        page = optionService.getOptionsForTariff(1, 5, 0);
        assertEquals(options5Dto, page.getData());
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertEquals(false, tmp.isExisting());
    }

    @Test
    public void getOptionsForNewOptionParentReturnsOptionsByPageAndOption() {
        when(optionDao.getAllNoParent(1, 1)).thenReturn(options);
        when(optionDao.getPagesCountNoParent()).thenReturn((long) 5);

        page = optionService.getOptionsForNewOption(1, 1, true);
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertEquals(optionsDto, page.getData());
        assertEquals(5, page.getTotalPages());
        assertEquals(null, tmp.getParent());
    }

    @Test
    public void getOptionsForNewOptionChildrenReturnsOptionsByPageAndOption() {
        when(optionMapper.entityToDto(option7)).thenReturn(option7Dto);

        when(optionDao.getAllNoChildrenAndParent(1, 1)).thenReturn(options7);
        when(optionDao.getPagesCountNoParentNoChildren()).thenReturn((long) 3);

        page = optionService.getOptionsForNewOption(1, 1, false);
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertEquals(options7Dto, page.getData());
        assertEquals(3, page.getTotalPages());
        assertEquals(null, tmp.getParent());
        assertEquals(null, tmp.getChildren());
    }

    @Test
    public void getOptionsForExistingOptionParentReturnsOptionsByPageAndOption() {

        when(optionDao.getAllNoParent(1, 1)).thenReturn(options);
        when(optionDao.getPagesCountNoParent()).thenReturn((long) 5);
        page = optionService.getPageForExisting(1, 1, true, 5);
        OptionDto tmp = (OptionDto) page.getData().get(0);

        assertEquals(optionsDto, page.getData());
        assertEquals(5, page.getTotalPages());
        assertEquals(true, tmp.isExisting());
    }

    @Test
    public void getOptionsForExistingOptionParentReturnsOptionsByPageAndOptionNoExisting() {


        when(optionDao.getAllNoParent(1, 1)).thenReturn(options7);
        when(optionDao.getPagesCountNoParent()).thenReturn((long) 5);
        page = optionService.getPageForExisting(1, 1, true, 0);
        OptionDto tmp = (OptionDto) page.getData().get(0);

        assertEquals(options7Dto, page.getData());
        assertEquals(5, page.getTotalPages());
        assertEquals(false, tmp.isExisting());
    }


    @Test
    public void getOptionsForExistingOptionChildrenReturnsOptionsByPageAndOption() {

        when(optionDao.getAllNoChildrenAndParentExisting(1, 1, 0)).thenReturn(options5);
        when(optionDao.getPagesNoChildrenAndParentExisting(0)).thenReturn((long) 3);

        page = optionService.getPageForExisting(1, 1, false, 0);
        OptionDto tmp = (OptionDto) page.getData().get(0);

        assertEquals(optionsDto, page.getData());
        assertEquals(3, page.getTotalPages());
        assertEquals(true, tmp.isExisting());
    }

    @Test
    public void getOptionsForExistingOptionChildrenReturnsOptionsByPageAndOptionNoExisting() {

        when(optionDao.getAllNoChildrenAndParentExisting(1, 1, 5)).thenReturn(options7);
        when(optionDao.getPagesNoChildrenAndParentExisting(5)).thenReturn((long) 3);
        page = optionService.getPageForExisting(1, 1, false, 5);
        OptionDto tmp = (OptionDto) page.getData().get(0);

        assertEquals(options7Dto, page.getData());
        assertEquals(3, page.getTotalPages());
        assertEquals(false, tmp.isExisting());
    }

    @Test
    public void getOptionsForOptionGroupsReturnsOptionsByOG() {
        when(optionDao.getAllNoParentNoGroup(1, 1)).thenReturn(options);
        when(optionDao.getPagesCountNoParentNoGroup()).thenReturn((long) 2);

        page = optionService.getOpionsForOptionGroup(1, 1);
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertEquals(optionsDto, page.getData());
        assertEquals(2, page.getTotalPages());
        assertEquals(null, tmp.getParent());
        assertEquals(null, tmp.getOptionGroup());
    }

    @Test
    public void getOptionsForExistingOptionGroupsReturnsOptionsByOG() {

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
    public void getOptionsForExistingOptionGroupsReturnsOptionsByOGNoExisting() {
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
        assertEquals(null, tmp.getOptionGroup());
        assertNotEquals(ogDto, tmp.getOptionGroup());
    }


    @Test
    public void setCompatibleTariffsSetsTariffs() {
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
    public void setCompatibleTariffsNoTariffsWithNull() {
        optionService.setCompatibleTariffs(optionDto, null);
        assertEquals(true, optionDto.getCompatibleTariffs().isEmpty());
    }


    @Test
    public void setChildrenSetsChildren() {
        List<Integer> id = new ArrayList<>();
        id.add(7);
        optionService.setCompatibleTariffs(optionDto, id);
        Set<OptionDto> tmp = optionDto.getChildren();
        for (OptionDto o : tmp) {
            assertEquals(7, o.getId());
            assertNotEquals(5, o.getId());
        }
    }


    @Test
    public void setChildrenNoChildrenWithNull() {
        optionService.setChildren(optionDto, null);
        assertEquals(true, optionDto.getChildren().isEmpty());
    }


    @Test
    public void setOptionGroupSetsOptionGroup() {
        optionService.setOptionGroup(optionDto, 1);
        assertEquals(1, optionDto.getOptionGroup().getId());
        assertNotEquals(5, optionDto.getOptionGroup().getId());
    }


    @Test
    public void setOptionGroupNoOptionGroupWithNull() {
        optionService.setOptionGroup(optionDto, null);
        assertEquals(null, optionDto.getOptionGroup());
        optionService.setOptionGroup(optionDto, 0);
        assertEquals(null, optionDto.getOptionGroup());
    }

    @Test
    public void setParentSetsParent() {
        optionService.setParent(option7Dto, 0);
        assertEquals(0, option7Dto.getParent().getId());
        assertNotEquals(5, option7Dto.getParent().getId());
    }


    @Test
    public void setParentNoParentWithNull() {
        optionService.setParent(option7Dto, null);
        assertEquals(null, option7Dto.getParent());
    }

    @Test
    public void findByTariffReturnsParentOptionsByTariff() {
        when(optionDao.findByTariffParents(0)).thenReturn(options7);
        assertEquals(options7Dto, optionService.findByTariff(0));
        assertNotEquals(null, optionService.findByTariff(0));
    }

    @Test
    public void findByTariffChildrenReturnsChildrenOptionsByTariff() {
        when(optionDao.findByTariffChildren(0)).thenReturn(options5);
        assertEquals(options5Dto, optionService.findByTariff(0));
        assertNotEquals(null, optionService.findByTariff(0));
    }

    @Test
    public void updateChildrenUpdatesChildrenWhenExistCurrentEmpty() {

        option.setChildren(new HashSet<>());
        option5.setParent(null);
        optionService.updateChildren(optionDto, option);
        assertEquals(0, option7.getParent().getId());
        assertEquals(null, option5.getParent());
        Set<Option> tmp = option.getChildren();
        for (Option o : tmp) {
            assertEquals(7, o.getId());
        }
    }

    @Test
    public void updateChildrenUpdatesChildrenWhenExistCurrent() {
        OptionGroup og7 = new OptionGroup();
        option7.setGroup(og7);

        assertEquals(0, option5.getParent().getId());
        Set<OptionDto> tmp = new HashSet<>();
        tmp.add(option7Dto);
        optionDto.setChildren(tmp);

        optionService.updateChildren(optionDto, option);
        assertEquals(0, option7.getParent().getId());
        assertEquals(null, option7.getGroup());
        assertEquals(null, option5.getParent());


    }

    @Test
    public void updateChildrenNoUpdatesCurrentEmpty() {
        OptionDto od = new OptionDto();
        od.setChildren(new HashSet<>());
        optionService.updateChildren(od, option7);
        assertEquals(true, option7.getChildren().isEmpty());
    }

    @Test
    public void updateChildrenNoUpdatesCurrentNotEmpty() {
        OptionDto od = new OptionDto();
        od.setChildren(new HashSet<>());
        assertEquals(option, option5.getParent());
        optionService.updateChildren(od, option);
        assertEquals(null, option5.getParent());
    }

    @Test
    public void updateChildrenNoUpdatesForChildren() {
        OptionDto od = new OptionDto();
        Set ods = new HashSet();
        ods.add(option7Dto);
        od.setChildren(ods);
        optionService.updateChildren(od, option5);
        assertEquals(true, option5.getChildren().isEmpty());
    }

    @Test
    public void updateTariffsUpdatesTariffWhenExistCurrentNotEmpty() {
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
        assertEquals(true, tariff.getOptions().isEmpty());
    }

    @Test
    public void updateTariffsUpdatesTariffWhenExistCurrentEmpty() {

        Set<TariffDto> tds = new HashSet<>();
        tds.add(tariffDto);
        optionDto.setCompatibleTariffs(tds);

        Set<Option> tmp2 = tariff.getOptions();
        for (Option o : tmp2) {
            assertEquals(0, o.getId());
        }
    }

    @Test
    public void updateTariffsNoUpdatesCurrentNotEmpty() {
        Set<Tariff> tds2 = new HashSet<>();
        tds2.add(tariff);
        option.setCompatibleTariffs(tds2);
        Set<Option> ods2 = new HashSet<>();
        ods2.add(option);
        tariff.setOptions(ods2);
        optionService.updateTariff(optionDto, option);
        Set<Option> tmp = tariff.getOptions();
        assertEquals(true, tmp.isEmpty());
    }

    @Test
    public void updateTariffsNoUpdatesCurrentEmpty() {
        optionService.updateTariff(optionDto, option);
        assertEquals(true, option.getCompatibleTariffs().isEmpty());
    }

    @Test
    public void updateOptionGroupUpdatesGroup() {
        optionDto.setOptionGroup(ogDto);
        optionService.updateOptionGroup(optionDto, option);
        assertEquals(og, option.getGroup());
    }

    @Test
    public void updateOptionGroupNoUpdates() {

        optionService.updateOptionGroup(optionDto, option);
        assertEquals(null, option.getGroup());
//dummy group
        OptionGroup og0 = new OptionGroup();
        og0.setId(0);
        OptionGroupDto og0Dto = new OptionGroupDto();
        og0Dto.setId(0);
        optionDto.setOptionGroup(og0Dto);
        when(optionGroupDao.getOne(0)).thenReturn(og0);
        assertEquals(null, option.getGroup());
//child option
        option5Dto.setOptionGroup(ogDto);
        optionService.updateOptionGroup(option5Dto, option5);
        assertEquals(null, option5.getGroup());
    }

    @Test
    public void updateOptionParentUpdatesParent() {
        assertEquals(option, option5.getParent());
        option5Dto.setParent(option7Dto);
        optionService.updateOptionParent(option5Dto, option5);
        assertEquals(option7, option5.getParent());
        assertNotEquals(option, option5.getParent());
    }

    @Test
    public void updateOptionParentNoUpdates() {
//no update
        optionService.updateOptionParent(option5Dto, option5);
        assertEquals(option, option5.getParent());

        //no group
        optionService.updateOptionParent(option7Dto, option7);
        assertEquals(null, option.getParent());

        //parent itself
        optionDto.setParent(optionDto);
        assertEquals(null, option.getParent());
        //parent is child
        option7Dto.setParent(option5Dto);
        assertEquals(null, option7.getParent());
    }

    @Test
    public void addAddsOption() {
        assertEquals(option, option5.getParent());
        option5Dto.setParent(option7Dto);
        optionService.updateOptionParent(option5Dto, option5);
        assertEquals(option7, option5.getParent());
        assertNotEquals(option, option5.getParent());
    }
}
/*
    @Test
    public void getAllValidReturnsAllValidTatiffs() {
        assertEquals(tariffsDto, tariffService.getAllValid());
        assertNotEquals(tariffs5Dto, tariffService.getAllValid());
        assertEquals(true, tariffService.getAllValid().get(0).isIsValid());
    }


    @Test
    public void getAllPromotedReturnsAllValidTatiffs() {
        assertNotEquals(tariffs5Dto, tariffService.getAllValid());
        assertEquals(tariffsDto, tariffService.getAllPromoted());
        assertEquals(true, tariffService.getAllPromoted().get(0).isPromoted());
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
    public void getAllPaginatedByOptionCheckExisting() {
        Set<Tariff> tariffsSet = new HashSet<>();
        tariffsSet.add(tariff);
        option.setCompatibleTariffs(tariffsSet);
        when(tariffDao.getPages(1, 1)).thenReturn(tariffs);

        page = tariffService.getAllPaginated(1, 1, 0);
        TariffDto tmp = (TariffDto) page.getData().get(0);

        assertEquals(tariffsDto, page.getData());
        assertEquals(true, tmp.isExisting());
    }

    @Test
    public void getAllPaginatedByOptionCheckNotExisting() {
        when(tariffMapper.entityToDto(tariff5)).thenReturn(tariff5Dto);
        option.setCompatibleTariffs(new HashSet<>());

        when(tariffDao.getPages(1, 5)).thenReturn(tariffs5);

        page = tariffService.getAllPaginated(1, 5, 0);
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
        tariffDto.setOptions(new HashSet<>());
        Set<OptionDto> tmp3 = tariffDto.getOptions();
        for (OptionDto o : tmp3) {
            assertNotEquals(0, o.getId());
            assertNotEquals(1, o.getId());
        }

        tariffService.add(tariffDto);
        tmp2 = tariff.getOptions();
        for (Option o : tmp2) {
            assertNotEquals(0, o.getId());
            assertNotEquals(1, o.getId());
        }
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

