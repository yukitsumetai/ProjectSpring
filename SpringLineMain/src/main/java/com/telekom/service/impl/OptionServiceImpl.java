package com.telekom.service.impl;


import com.telekom.dao.OptionDao;
import com.telekom.dao.OptionGroupDao;
import com.telekom.dao.TariffDao;
import com.telekom.model.entity.Option;
import com.telekom.model.entity.OptionGroup;
import com.telekom.model.entity.Tariff;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.OptionGroupDto;
import com.telekom.model.dto.Page;
import com.telekom.model.dto.TariffDto;
import com.telekom.mapper.OptionMapper;
import com.telekom.service.api.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional(readOnly = true)
public class OptionServiceImpl extends PaginationImpl<OptionDto> implements OptionService {


    @Autowired
    private OptionDao optionDao;
    @Autowired
    private OptionGroupDao optionGroupDao;
    @Autowired
    private TariffDao tariffDao;
    @Autowired
    private OptionMapper optionMapper;

    private List<OptionDto> listEntityToDto(List<Option> options) {
        List<OptionDto> optionsDto = new ArrayList<>();
        for (Option t : options) {
            optionsDto.add(optionMapper.entityToDto(t));
        }
        return optionsDto;

    }

    @Override
    public List<OptionDto> getAll() { //who uses this??

        List<Option> options = optionDao.getAll();
        return listEntityToDto(options);
    }

    @Override
    public List<OptionDto> getAll(TariffDto tariff) {

        List<OptionDto> options = listEntityToDto(optionDao.getAll());
        for (OptionDto o : options
        ) {
            for (OptionDto e : tariff.getOptions()
            ) {
                if (o.getId() == e.getId()) {
                    o.setExisting(true);
                }
            }
        }
        return options;
    }

    @Override
    public List<OptionDto> getAllValid() { //who uses this??

        List<Option> options = optionDao.getAllValid();
        return listEntityToDto(options);
    }

    @Override
    public Page<OptionDto> getPageForNew(Integer size, Integer page, boolean parent) {
        List<OptionDto> pageGroups;
        Long total;
        if (parent) {
            pageGroups = listEntityToDto(optionDao.getAllNoParent(size, page));
            total = optionDao.getPagesCountNoParent();
        } else {
            pageGroups = listEntityToDto(optionDao.getAllNoChildrenAndParent(size, page));
            total = optionDao.getPagesCountNoParentNoChildren();
        }
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    public Page<OptionDto> getPageForExisting(Integer size, Integer page, boolean parent, Integer optionId) {
        List<OptionDto> pageGroups;
        Option o = optionDao.getOne(optionId);
        Long total;
        if (parent) {
            pageGroups = listEntityToDto(optionDao.getAllNoParent(size, page));
            Option parent2 = o.getParent();
            if (parent2 != null) {
                for (OptionDto o2 : pageGroups
                ) {
                    if (o.getParent().getId() == o2.getId()) o2.setExisting(true);
                    if (o.getId() == o2.getId()) o2.setExisting(true);
                }
            }
            total = optionDao.getPagesCountNoParent();
        } else {
            pageGroups = listEntityToDto(optionDao.getAllNoChildrenAndParentExisting(size, page, optionId));
            for (OptionDto o2 : pageGroups) {
                for (Option c : o.getChildren()) {
                    if (c.getId() == o2.getId()) {
                        o2.setExisting(true);
                    }
                }
            }
            total = optionDao.getPagesNoChildrenAndParentExisting(optionId);
        }
        pageGroups.removeIf(st -> st.getId() == o.getId());
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<OptionDto> getPage(Integer size, Integer page) {
        List<OptionDto> pageGroups = listEntityToDto(optionDao.getPages(size, page));
        Long total = optionDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<OptionDto> getPage(Integer size, Integer page, Integer tariffId) {
        List<OptionDto> pageGroups = listEntityToDto(optionDao.getPages(size, page));
        Set<Option> existing = tariffDao.getOne(tariffId).getOptions();
        for (Option i : existing
        ) {
            for (OptionDto o : pageGroups
            ) {
                if (o.getId() == i.getId()) o.setExisting(true);
            }
        }
        Long total = optionDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<OptionDto> getPageForGroup(Integer size, Integer page) {
        List<OptionDto> pageGroups = listEntityToDto(optionDao.getAllNoParentNoGroup(size, page));
        Long total = optionDao.getPagesCountNoParentNoGroup();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<OptionDto> getPageForExistingGroup(Integer size, Integer page, Integer optionGroupId) {
        OptionGroup og = optionGroupDao.getOne(optionGroupId);
        List<OptionDto>pageGroups = listEntityToDto(optionDao.getAllNoParentNoGroupExisting(size, page, optionGroupId));
        for (OptionDto o2 : pageGroups) {
            for (Option o : og.getOptions()) {
                if (o.getId() == o2.getId()) {
                    o2.setExisting(true);
                }
            }
        }
        Long total = optionDao.getPagesCountNoParentNoGroupExisting(optionGroupId);
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    public void setCompatibleTariffs(OptionDto option, List<Integer> id) {
        Set<TariffDto> tariffs = new HashSet<>();
        if (id != null) {
            for (Integer i : id) {
                TariffDto t = new TariffDto();
                t.setId(i);
                tariffs.add(t);
            }
        }
        option.setCompatibleTariffs(tariffs);
    }

    @Override
    public void setChildren(OptionDto option, List<Integer> id) {
        if (id != null) {
            Set<OptionDto> children = new HashSet<>();
            for (Integer i : id) {
                OptionDto o = new OptionDto();
                o.setId(i);
                children.add(o);
            }
            option.setChildren(children);
        }
    }

    @Override
    public void setOptionGroup(OptionDto option, Integer groupId) {
        if (groupId != null) {
            if (groupId > 0) {
                OptionGroupDto og = new OptionGroupDto();
                og.setId(groupId);
                option.setOptionGroup(og);
            } else option.setOptionGroup(null);
        } else option.setOptionGroup(null);
    }

    @Override
    public void setParent(OptionDto option, Integer id) {
        if (id != null) {
            OptionDto o = new OptionDto();
            o.setId(id);
            option.setParent(o);
        } else option.setParent(null);
    }

    @Override
    public Set<OptionDto> findByTariff(Integer id) {

        List<Option> options = optionDao.findByTariffParents(id); //valid only
        Set<OptionDto> optionsDto = new HashSet<>();
        for (Option t : options) {
            optionsDto.add(optionMapper.entityToDto(t));
        }
        return optionsDto;
    }

    @Override
    public Set<OptionDto> findByTariffChildren(Integer id) {
        List<Option> options = optionDao.findByTariffChildren(id);
        Set<OptionDto> optionsDto = new HashSet<>();
        for (Option t : options) {
            optionsDto.add(optionMapper.entityToDto(t));
        }
        return optionsDto;
    }

    private void updateOptionRelations(OptionDto option, Option o) {
        //Add children if exist
        if (!option.getChildren().isEmpty()) {
            Set<OptionDto> children = option.getChildren();
            for (OptionDto child : children
            ) {
                Option tmp = optionDao.getOne(child.getId());
                if (o != tmp && tmp.getParent() == null && tmp.getChildren().isEmpty()) {
                    tmp.setParent(o);
                }
            }
        }
        //Remove current tariffs
        if (!o.getCompatibleTariffs().isEmpty()) {
            Set<Tariff> tariffs = o.getCompatibleTariffs();
            for (Tariff t : tariffs
            ) {
                Tariff tmp = tariffDao.getOne(t.getId());
                tmp.removeOption(o);
            }
        }
//Add tariffs if exist
        if (!option.getCompatibleTariffs().isEmpty()) {
            Set<TariffDto> tariffs = option.getCompatibleTariffs();
            for (TariffDto t : tariffs
            ) {
                Tariff tmp = tariffDao.getOne(t.getId());
                tmp.addOption(o);
            }
        }


    }

    private void updateOptionParent(OptionDto option, Option o) {
        //Set parent if exist
        OptionDto p = option.getParent();
        if (p != null) {
            Option tmp = optionDao.getOne(p.getId());
            if (o != tmp && tmp.getParent() == null) {
                o.setParent(tmp);
            }
        } else o.setParent(null);
    }

    private void updateOptionGroup(OptionDto option, Option o) {
        //Set group if exist
        OptionGroupDto p = option.getOptionGroup();
        if (p != null) {
            OptionGroup tmp = optionGroupDao.getOne(p.getId());
            if (tmp != null) { //dummy group - 0
                o.setGroup(tmp);
            }
        } else o.setGroup(null);
    }

    @Override
    @Transactional
    public void add(OptionDto option) {
        Option o = optionMapper.dtoToEntity(option);
        updateOptionParent(option, o);
        if (o.getParent() == null) {
            updateOptionGroup(option, o);
        }
//add option to DB
        optionDao.add(o);
        updateOptionRelations(option, o);
    }


    @Override
    public OptionDto getOne(int id) { //for 0 option
        Option t = optionDao.getOne(id);
        if (t != null) return optionMapper.entityToDto(t);
        else return null;
    }


    @Override
    @Transactional
    public void editOption(OptionDto option) {
        Option o = optionDao.getOne(option.getId());
        o.setValid(option.isIsValid());
        //Remove children if exist
        if (!o.getChildren().isEmpty()) {
            Set<Option> children = o.getChildren();
            for (Option child : children
            ) {
                Option tmp = optionDao.getOne(child.getId());
                tmp.setParent(null);
                tmp.setGroup(null);
            }
        }
        updateOptionParent(option, o);
        if (o.getParent() == null) {
            updateOptionGroup(option, o);
        } else o.setGroup(null);
        option.setDescription(o.getDescription());
        updateOptionRelations(option, o);
    }

    @Override
    public void removeRelations(OptionDto option) {
        option.setParent(null);
        option.setChildren(new HashSet<>());
    }

    @Override
    @Transactional
    public void deleteOption(Integer id) {
        Option option = optionDao.getOne(id);
        option.setValid(false);
    }
}