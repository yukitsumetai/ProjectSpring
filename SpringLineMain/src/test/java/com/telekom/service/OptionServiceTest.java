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
import com.telekom.service.api.OptionService;
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
    OptionService optionService;
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

    @BeforeAll
    public void setup() {
        option = new Option();
        option.setId(0);
        option.setValid(true);
        Set children = new HashSet();


        option5 = new Option();
        option5.setId(5);
        option5.setValid(false);
        option5.setParent(option);
        children.add(option5);
        option.setChildren(children);

        optionDto = new OptionDto();
        optionDto.setId(0);
        optionDto.setIsValid(true);
        Set childrenDto = new HashSet();


        option5Dto = new OptionDto();
        option5Dto.setId(0);
        option5Dto.setIsValid(false);
        option5Dto.setParent(optionDto);

        childrenDto.add(option5Dto);
        option.setChildren(childrenDto);

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
        options7 = new ArrayList<>();
        options7.add(option7);

        option7Dto = new OptionDto();
        option7Dto.setId(7);
        options7Dto = new ArrayList<>();
        options7Dto.add(option7Dto);

        tariff = new Tariff();
        tariff.setId(0);

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
        OptionGroup og=new OptionGroup();
        og.setId(0);
        OptionGroupDto ogDto=new OptionGroupDto();
        ogDto.setId(0);
        option7.setGroup(og);
        option7Dto.setOptionGroup(ogDto);

        when(optionGroupDao.getOne(og.getId())).thenReturn(og);
        when(optionDao.getAllNoParentNoGroupExisting(1, 1, og.getId())).thenReturn(options7);
        when(optionDao.getPagesCountNoParentNoGroupExisting(og.getId())).thenReturn((long) 8);

        page = optionService. getOptionsForExistingOptionGroup(1, 1, og.getId());
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertEquals(options7Dto, page.getData());
        assertEquals(8, page.getTotalPages());
        assertEquals(ogDto, tmp.getOptionGroup());

    }

    @Test
    public void getOptionsForExistingOptionGroupsReturnsOptionsByOGNoExisting() {
        OptionGroup og=new OptionGroup();
        og.setId(0);
        OptionGroupDto ogDto=new OptionGroupDto();
        ogDto.setId(0);

        when(optionGroupDao.getOne(og.getId())).thenReturn(og);
        when(optionDao.getAllNoParentNoGroupExisting(1, 1, og.getId())).thenReturn(options5);
        when(optionDao.getPagesCountNoParentNoGroupExisting(og.getId())).thenReturn((long) 8);

        page = optionService. getOptionsForExistingOptionGroup(1, 1, og.getId());
        OptionDto tmp = (OptionDto) page.getData().get(0);
        assertEquals(options5Dto, page.getData());
        assertEquals(8, page.getTotalPages());
        assertEquals(null, tmp.getOptionGroup());
        assertNotEquals(ogDto, tmp.getOptionGroup());
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

