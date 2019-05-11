package com.telekom.service;


import com.telekom.config.OptionGroupServiceConfig;
import com.telekom.dao.api.OptionDao;
import com.telekom.dao.api.OptionGroupDao;
import com.telekom.mapper.OptionGroupMapper;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.OptionGroupDto;
import com.telekom.model.dto.Page;
import com.telekom.model.entity.Option;
import com.telekom.model.entity.OptionGroup;
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

import static org.junit.jupiter.api.Assertions.*;
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
    private Page page;
    private static OptionGroup og;
    private static OptionGroupDto ogDto;
    private static List<OptionGroup> ogs;
    private static List<OptionGroupDto> ogsDto;

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
        when(optionGroupMapper.dtoToEntity(ogDto)).thenReturn(og);

        when(optionDao.getOne(0)).thenReturn(option);
        when(optionDao.getOne(5)).thenReturn(option5);

    }

    @Test
    void getAllReturnsAllOptionGroups() {
        when(optionGroupDao.getAll()).thenReturn(ogs);
        assertEquals(ogsDto, optionGroupService.getAll());
    }

    @Test
    void getAllValidReturnsAllValidOptionGroups() {
        when(optionGroupDao.getAllValid()).thenReturn(ogs);
        assertEquals(ogsDto, optionGroupService.getAllValid());
        assertTrue(optionGroupService.getAllValid().get(0).isIsValid());

        }

    @Test
    void getPageReturnsOptionGroupsPaged() {
        when(optionGroupDao.getPages(1, 1)).thenReturn(ogs);
        when(optionGroupDao.getPages(1, 7)).thenReturn(null);
        when(optionGroupDao.getPagesCount()).thenReturn((long)6);

        page = optionGroupService.getPage(1, 1);
        assertEquals(ogsDto, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(6, page.getTotalPages());
        assertEquals(1, page.getLastPage());


        page = optionGroupService.getPage(1, 7);
        assertTrue(page.getData().isEmpty());
        assertEquals(7, page.getCurrentPage());
    }

    @Test
    void getOptionGroupReturnsOptionGroup() {
        when(optionGroupDao.getOne(5)).thenReturn(og);
        when(optionGroupDao.getOne(7)).thenReturn(null);

        assertEquals(ogDto, optionGroupService.getOptionGroup(1));
        assertNull(optionGroupService.getOptionGroup(7));
    }


    @Test
    void addOptionsAddsOptionsIfExist() {
        ogDto.addOption(optionDto);
        optionGroupService.addOptions(ogDto, og);
        assertEquals(og, option.getGroup());
    }

    @Test
    void findByTariffFindsByTariff() {
        when(optionGroupDao.findByTariff(0)).thenReturn(ogs);
        optionGroupService.findByTariff(0);
        assertIterableEquals(ogsDto,   optionGroupService.findByTariff(0));
    }

    @Test
    void deleteOptionsRemovesOptionsFromOG() {
        og.addOption(option);
        option.setGroup(og);
        optionGroupService.deleteOptions(og);
        assertNull(option.getGroup());
    }


    @Test
    void getByNameReturnsOptionGroup() {
        when(optionGroupDao.findByName("test")).thenReturn(ogs);
        assertIterableEquals(ogsDto, optionGroupService.getByName("test"));
    }

    @Test
    void getByNameReturnsEmptyWhenFoundNothing() {
        when(optionGroupDao.findByName("test2")).thenReturn(null);
        assertTrue(optionGroupService.getByName("test2").isEmpty());
    }

    @Test
    void addAddsOG() {
        optionGroupService.add(ogDto);
      verify(optionGroupDao).add(og);
    }

    @Test
    void editOptionGroupEditsOg() {
        ogDto.setIsValid(true);
        ogDto.setDescription("test");
        option5.setGroup(og);
        og.addOption(option5);
        ogDto.addOption(optionDto);
        optionGroupService.editOptionGroup(ogDto);
        assertTrue(og.isValid());
        assertEquals(og, option.getGroup());
        assertEquals("test", og.getDescription());
        assertNotEquals(og, option5.getGroup());
    }

    @Test
    void deleteOptionGroupRemovesOptions() {
        option5.setGroup(og);
        og.addOption(option5);
        og.setValid(true);
        optionGroupService.deleteOptionGroup(1);
        assertFalse(og.isValid());
        assertNull(option5.getGroup());
    }
}
