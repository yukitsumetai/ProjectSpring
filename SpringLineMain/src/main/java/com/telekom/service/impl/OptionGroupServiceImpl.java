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


    private List<OptionGroupDto> listEntityToDto(List<OptionGroup> optionGroups) {
        List<OptionGroupDto> optionGroupsDTO = new ArrayList<>();
        if(optionGroups!=null) {
            for (OptionGroup t : optionGroups) {

                optionGroupsDTO.add(optionGroupMapper.entityToDto(t));
            }
        }
        return optionGroupsDTO;
    }

    @Override
    public void setOptionsDto(OptionGroupDto optionGroup, List<Integer> id) {
        setOptions(optionGroup,  id);
    }

    @Override
    @Transactional
    public List<OptionGroupDto> getAll() {
        List<OptionGroup> optionGroups = optionGroupDao.getAll();
        return listEntityToDto(optionGroups);
    }

    @Transactional
    public List<OptionGroupDto> getAllValid() {
        List<OptionGroup> optionGroups = optionGroupDao.getAllValid();
        return listEntityToDto(optionGroups);
    }

    @Override
    @Transactional
    public Page<OptionGroupDto> getPage(Integer size, Integer page) {
        List<OptionGroupDto> pageGroups = listEntityToDto(optionGroupDao.getPages(size, page));
        Long total=optionGroupDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public OptionGroupDto getOptionGroup(int id) {

        OptionGroup t = optionGroupDao.getOne(id);
        return optionGroupMapper.entityToDto(t);

    }

   public void addOptions(OptionGroupDto optionGroup, OptionGroup t) {
        if (!optionGroup.getOptions().isEmpty()) {
            Set<OptionDto> options = optionGroup.getOptions();
            for (OptionDto o : options
            ) {
                Option tmp = optionDao.getOne(o.getId());
                if (tmp != null) { tmp.setGroup(t); }
            }
        }
    }

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


   public void deleteOptions(OptionGroup t) {
        if (!t.getOptions().isEmpty()) {
            Set<Option> options = t.getOptions();
            for (Option o : options
            ) {
                Option tmp = optionDao.getOne(o.getId());
                    tmp.setGroup(null);
            }
        }
    }

    public List<OptionGroupDto> getByName(String name) {

        List<OptionGroup> op = optionGroupDao.findByName(name);
        return listEntityToDto(op);

    }

    @Override
    @Transactional
    public void add(OptionGroupDto optionGroup) {
        OptionGroup t = optionGroupMapper.dtoToEntity(optionGroup);
        optionGroupDao.add(t);
        addOptions(optionGroup, t);
    }


    @Override
    @Transactional
    public void editOptionGroup(OptionGroupDto t) {
        OptionGroup optionGroup = optionGroupDao.getOne(t.getId());
        optionGroup.setValid(t.isIsValid());
        optionGroup.setDescription(t.getDescription());
        deleteOptions(optionGroup);
        addOptions(t, optionGroup);
    }


    @Override
    @Transactional
    public void deleteOptionGroup(Integer id) {
        OptionGroup optionGroup = optionGroupDao.getOne(id);
        deleteOptions(optionGroup);
        optionGroup.setValid(false);
    }

}
