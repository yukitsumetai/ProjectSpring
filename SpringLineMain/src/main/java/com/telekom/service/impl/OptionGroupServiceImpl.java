package com.telekom.service.impl;

import com.telekom.dao.api.OptionDao;
import com.telekom.dao.api.OptionGroupDao;
import com.telekom.model.entity.Option;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.OptionGroupDto;
import com.telekom.model.dto.Page;
import com.telekom.mapper.OptionGroupMapper;
import com.telekom.model.entity.OptionGroup;
import com.telekom.service.api.OptionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OptionGroupServiceImpl extends SharedFunctions<OptionGroupDto> implements OptionGroupService {

    @Autowired
    private OptionGroupDao optionGroupDao;
    @Autowired
    private OptionDao optionDao;
    @Autowired
    private OptionGroupMapper optionGroupMapper;

    /**
     * This function maps list of option groupss to list of optiongroups Dto
     * @param optionGroups
     * @return list of option groups Dto mapped from option groups
     */
    private List<OptionGroupDto> listEntityToDto(List<OptionGroup> optionGroups) {
        List<OptionGroupDto> optionGroupsDTO = new ArrayList<>();
        if(optionGroups!=null) {
            for (OptionGroup t : optionGroups) {

                optionGroupsDTO.add(optionGroupMapper.entityToDto(t));
            }
        }
        return optionGroupsDTO;
    }

    /**
     * Sets compatible options to option group Dto
     * @param optionGroup
     * @param id ids of compatible options
     */
    @Override
    public void setOptionsDto(OptionGroupDto optionGroup, List<Integer> id) {
        setOptions(optionGroup,  id);
    }

    /**
     * Returns list of all option groups Dto
     * @return list option group Dto
     */
    @Override
    @Transactional
    public List<OptionGroupDto> getAll() {
        List<OptionGroup> optionGroups = optionGroupDao.getAll();
        return listEntityToDto(optionGroups);
    }

    /**
     * Returns list of all valid option groups
     * @return list option group Dto
     */
    @Transactional
    public List<OptionGroupDto> getAllValid() {
        List<OptionGroup> optionGroups = optionGroupDao.getAllValid();
        return listEntityToDto(optionGroups);
    }

    /**
     * Returns a page of option group Dto (eg for dropdown)
     * @param size page size
     * @param page number of page
     * @return page of option group Dto with list of option Dto and page parameters
     */
    @Override
    @Transactional
    public Page<OptionGroupDto> getPage(Integer size, Integer page) {
        List<OptionGroupDto> pageGroups = listEntityToDto(optionGroupDao.getPages(size, page));
        Long total=optionGroupDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    /**
     * Returns option group Dto by id
     * @param id
     * @return  option group Dto
     */
    @Override
    @Transactional
    public OptionGroupDto getOptionGroup(int id) {

        OptionGroup t = optionGroupDao.getOne(id);
        return optionGroupMapper.entityToDto(t);

    }

    /**
     * Adds compatible options to option group in DB
     * @param optionGroupDto
     * @param optionGroup
     */
   public void addOptions(OptionGroupDto optionGroupDto, OptionGroup optionGroup) {
        if (!optionGroupDto.getOptions().isEmpty()) {
            Set<OptionDto> options = optionGroupDto.getOptions();
            for (OptionDto o : options
            ) {
                Option tmp = optionDao.getOne(o.getId());
                if (tmp != null) { tmp.setGroup(optionGroup); }
            }
        }
    }

    /**
     * Returns option groups that belongs to options which are compatible with a given tariff
     * @param id id of a tariff
     * @return set of option groups Dto
     */
    @Override
    @Transactional
    public Set<OptionGroupDto> findByTariff(Integer id) {

        List<OptionGroup> options = optionGroupDao.findByTariff(id);
        Set<OptionGroupDto> optionGroupDto = new HashSet<>();
        for (OptionGroup t : options) {
            optionGroupDto.add(optionGroupMapper.entityToDto(t));
        }
        return optionGroupDto;
    }

    /**
     * Removes compatible options from an option group
     * @param optionGroup
     */
   public void deleteOptions(OptionGroup optionGroup) {
        if (!optionGroup.getOptions().isEmpty()) {
            Set<Option> options = optionGroup.getOptions();
            for (Option o : options
            ) {
                Option tmp = optionDao.getOne(o.getId());
                    tmp.setGroup(null);
            }
        }
    }

    /**
     * Returns list of option groups by its name
     * @param name
     * @return list of optin group Dto or null if not found
     */
    public List<OptionGroupDto> getByName(String name) {

        List<OptionGroup> op = optionGroupDao.findByName(name);
        return listEntityToDto(op);

    }

    /**
     * Adds option Group to DB
     * @param optionGroup
     */
    @Override
    @Transactional
    public void add(OptionGroupDto optionGroup) {
        OptionGroup t = optionGroupMapper.dtoToEntity(optionGroup);
        optionGroupDao.add(t);
        addOptions(optionGroup, t);
    }

    /**
     * Updates existiong option group in DB
     * @param optionGroupDto
     */
    @Override
    @Transactional
    public void editOptionGroup(OptionGroupDto optionGroupDto) {
        OptionGroup optionGroup = optionGroupDao.getOne(optionGroupDto.getId());
        optionGroup.setValid(optionGroupDto.isIsValid());
        optionGroup.setDescription(optionGroupDto.getDescription());
        deleteOptions(optionGroup);
        addOptions(optionGroupDto, optionGroup);
    }

    /**
     * Sets option group as invalid and removes all compatible options
     * @param id id of an option group
     */
    @Override
    @Transactional
    public void deleteOptionGroup(Integer id) {
        OptionGroup optionGroup = optionGroupDao.getOne(id);
        deleteOptions(optionGroup);
        optionGroup.setValid(false);
    }

}
