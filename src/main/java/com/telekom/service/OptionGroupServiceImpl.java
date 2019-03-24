package com.telekom.service;

import com.telekom.dao.OptionDao;
import com.telekom.dao.OptionGroupDao;
import com.telekom.entity.Option;
import com.telekom.entity.OptionGroup;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.OptionGroupDTO;
import com.telekom.mapper.OptionGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OptionGroupServiceImpl implements OptionGroupService {

    @Autowired
    private OptionGroupDao optionGroupDao;
    @Autowired
    private OptionDao optionDao;
    @Autowired
    private OptionGroupMapper optionGroupMapper;

    private List<OptionGroupDTO> listEntityToDto(List<OptionGroup> optionGroups){
        List<OptionGroupDTO> optionGroupsDTO = new ArrayList<>();
        for (OptionGroup t : optionGroups) {

            optionGroupsDTO.add(optionGroupMapper.EntityToDto(t));
        }
        return optionGroupsDTO;
    }
    @Override
    public void SetOptions(OptionGroupDTO optionGroup, List<Integer> id) {
        Set<OptionDTO> options = new HashSet<>();
        if (id != null) {
            for (Integer i : id) {
                OptionDTO t = new OptionDTO();
                t.setId(i);
                options.add(t);
            }
            optionGroup.setOptions(options);
        }
        else optionGroup.setOptions(new HashSet<>());

    }

    @Override
    @Transactional
    public List<OptionGroupDTO> getAll() {

        List<OptionGroup> optionGroups = optionGroupDao.getAll();
        List<OptionGroupDTO> optionGroupsDTO = new ArrayList<>();
        for (OptionGroup t : optionGroups) {

            optionGroupsDTO.add(optionGroupMapper.EntityToDto(t));
        }
        return listEntityToDto( optionGroups);
    }

    @Override
    @Transactional
    public List<OptionGroupDTO> getAllValid() {

        List<OptionGroup> optionGroups = optionGroupDao.getAllValid();
        return listEntityToDto( optionGroups);
    }



    @Override
    @Transactional
    public OptionGroupDTO getOne(int id) {

        OptionGroup t = optionGroupDao.getOne(id);
        return optionGroupMapper.EntityToDto(t);

    }

    private void addOptions(OptionGroupDTO optionGroup, OptionGroup t) {
        if (optionGroup.getOptions().size() > 0) {
            Set<OptionDTO> options = optionGroup.getOptions();
            for (OptionDTO o : options
            ) {
                Option tmp = optionDao.getOne(o.getId());
                if (tmp != null) {
                    if (tmp.isValid() == true) tmp.setGroup(t);
                }
            }
        }
    }

    private void deleteOptions(OptionGroup t) {
        if (t.getOptions().size() > 0) {
            Set<Option> options = t.getOptions();
            for (Option o : options
            ) {
                Option tmp = optionDao.getOne(o.getId());
                if (tmp != null) {
                    tmp.setGroup(null);
                }
            }
        }
    }

    @Override
    @Transactional
    public void add(OptionGroupDTO optionGroup) {
        OptionGroup t = optionGroupMapper.DtoToEntity(optionGroup);
        optionGroupDao.add(t);
        addOptions(optionGroup, t);
    }


    @Override
    @Transactional
    public void editOptionGroup(OptionGroupDTO t) {
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
