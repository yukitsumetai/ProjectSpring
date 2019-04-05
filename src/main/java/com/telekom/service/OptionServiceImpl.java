package com.telekom.service;


import com.telekom.dao.OptionDao;
import com.telekom.dao.OptionGroupDao;
import com.telekom.dao.TariffDao;
import com.telekom.entity.Option;
import com.telekom.entity.OptionGroup;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.OptionGroupDTO;
import com.telekom.entityDTO.Page;
import com.telekom.entityDTO.TariffDTO;
import com.telekom.mapper.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional(readOnly = true)
public class OptionServiceImpl extends PaginationImpl<OptionDTO> implements OptionService {


    @Autowired
    private OptionDao optionDao;
    @Autowired
    private OptionGroupDao optionGroupDao;
    @Autowired
    private TariffDao tariffDao;
    @Autowired
    private OptionMapper optionMapper;

    private List<OptionDTO> listEntityToDto(List<Option> options) {
        List<OptionDTO> OptionsDTO = new ArrayList<>();
        for (Option t : options) {
            OptionsDTO.add(optionMapper.EntityToDto(t));
        }
        return OptionsDTO;

    }

    @Override
    public List<OptionDTO> getAll() { //who uses this??

        List<Option> options = optionDao.getAll();
        return listEntityToDto(options);
    }

    @Override
    public List<OptionDTO> getAll(TariffDTO tariff) {

        List<OptionDTO> options = listEntityToDto(optionDao.getAll());
        for (OptionDTO o : options
        ) {
            for (OptionDTO e : tariff.getOptions()
            ) {
                if (o.getId() == e.getId()) {
                    o.setExisting(true);
                }
            }
        }
        return options;
    }

    @Override
    public List<OptionDTO> getAllValid() { //who uses this??

        List<Option> options = optionDao.getAllValid();
        return listEntityToDto(options);
    }

    @Override
    public Page<OptionDTO> getPageForNew(Integer size, Integer page, boolean parent) {
        List<OptionDTO> pageGroups;
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
    public Page<OptionDTO> getPageForExisting(Integer size, Integer page, boolean parent, Integer optionId) {
        List<OptionDTO> pageGroups;
        Option o = optionDao.getOne(optionId);
        Long total;
        if (parent) {
            pageGroups = listEntityToDto(optionDao.getAllNoParent(size, page));
            Option parent2 = o.getParent();
            if (parent2 != null) {
                for (OptionDTO o2 : pageGroups
                ) {
                    if (o.getParent().getId() == o2.getId()) o2.setExisting(true);
                    if (o.getId() == o2.getId()) o2.setExisting(true);
                }
            }
            total = optionDao.getPagesCountNoParent();
        } else {
            pageGroups = listEntityToDto(optionDao.getAllNoChildrenAndParentExisting(size, page, optionId));
            for (OptionDTO o2 : pageGroups) {
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
    public Page<OptionDTO> getPage(Integer size, Integer page) {
        List<OptionDTO> pageGroups = listEntityToDto(optionDao.getPages(size, page));
        Long total = optionDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<OptionDTO> getPage(Integer size, Integer page, Integer tariffId) {
        List<OptionDTO> pageGroups = listEntityToDto(optionDao.getPages(size, page));
        Set<Option> existing = tariffDao.getOne(tariffId).getOptions();
        for (Option i : existing
        ) {
            for (OptionDTO o : pageGroups
            ) {
                if (o.getId() == i.getId()) o.setExisting(true);
            }
        }
        Long total = optionDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<OptionDTO> getPageForGroup(Integer size, Integer page) {
        List<OptionDTO> pageGroups = listEntityToDto(optionDao.getAllNoParentNoGroup(size, page));
        Long total = optionDao.getPagesCountNoParentNoGroup();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<OptionDTO> getPageForExistingGroup(Integer size, Integer page, Integer optionGroupId) {
        OptionGroup og = optionGroupDao.getOne(optionGroupId);
        List<OptionDTO>pageGroups = listEntityToDto(optionDao.getAllNoParentNoGroupExisting(size, page, optionGroupId));
        for (OptionDTO o2 : pageGroups) {
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
    public void SetCompatibleTariffs(OptionDTO option, List<Integer> id) {
        Set<TariffDTO> tariffs = new HashSet<>();
        if (id != null) {
            for (Integer i : id) {
                TariffDTO t = new TariffDTO();
                t.setId(i);
                tariffs.add(t);
            }
        }
        option.setCompatibleTariffs(tariffs);
    }

    @Override
    public void SetChildren(OptionDTO option, List<Integer> id) {
        if (id != null) {
            Set<OptionDTO> children = new HashSet<>();
            for (Integer i : id) {
                OptionDTO o = new OptionDTO();
                o.setId(i);
                children.add(o);
            }
            option.setChildren(children);
        }
    }

    @Override
    public void SetOptionGroup(OptionDTO option, Integer groupId) {
        if (groupId != null) {
            if (groupId > 0) {
                OptionGroupDTO og = new OptionGroupDTO();
                og.setId(groupId);
                option.setOptionGroup(og);
            } else option.setOptionGroup(null);
        } else option.setOptionGroup(null);
    }

    @Override
    public void SetParent(OptionDTO option, Integer id) {
        if (id != null) {
            OptionDTO o = new OptionDTO();
            o.setId(id);
            option.setParent(o);
        } else option.setParent(null);
    }

    @Override
    public Set<OptionDTO> findByTariff(Integer id) {

        List<Option> Options = optionDao.findByTariffParents(id); //valid only
        Set<OptionDTO> OptionsDTO = new HashSet<>();
        for (Option t : Options) {
            OptionsDTO.add(optionMapper.EntityToDto(t));
        }
        return OptionsDTO;
    }

    @Override
    public Set<OptionDTO> findByTariffChildren(Integer id) {
        List<Option> Options = optionDao.findByTariffChildren(id);
        Set<OptionDTO> OptionsDTO = new HashSet<>();
        for (Option t : Options) {
            OptionsDTO.add(optionMapper.EntityToDto(t));
        }
        return OptionsDTO;
    }

    private void updateOptionRelations(OptionDTO option, Option o) {
        //Add children if exist
        if (option.getChildren().size() > 0) {
            Set<OptionDTO> children = option.getChildren();
            for (OptionDTO child : children
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
        if (option.getCompatibleTariffs().size() > 0) {
            Set<TariffDTO> tariffs = option.getCompatibleTariffs();
            for (TariffDTO t : tariffs
            ) {
                Tariff tmp = tariffDao.getOne(t.getId());
                tmp.addOption(o);
            }
        }


    }

    private void updateOptionParent(OptionDTO option, Option o) {
        //Set parent if exist
        OptionDTO p = option.getParent();
        if (p != null) {
            Option tmp = optionDao.getOne(p.getId());
            if (o != tmp && tmp.getParent() == null) {
                o.setParent(tmp);
            }
        } else o.setParent(null);
    }

    private void updateOptionGroup(OptionDTO option, Option o) {
        //Set group if exist
        OptionGroupDTO p = option.getOptionGroup();
        if (p != null) {
            OptionGroup tmp = optionGroupDao.getOne(p.getId());
            if (tmp != null) { //dummy group - 0
                o.setGroup(tmp);
            }
        } else o.setGroup(null);
    }

    @Override
    @Transactional
    public void add(OptionDTO option) {
        Option o = optionMapper.DtoToEntity(option);
        updateOptionParent(option, o);
        if (o.getParent() == null) {
            updateOptionGroup(option, o);
        }
//add option to DB
        optionDao.add(o);
        updateOptionRelations(option, o);
    }


    @Override
    public OptionDTO getOne(int id) { //for 0 option
        Option t = optionDao.getOne(id);
        if (t != null) return optionMapper.EntityToDto(t);
        else return null;
    }


    @Override
    @Transactional
    public void editOption(OptionDTO option) {
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
    public void removeRelations(OptionDTO option) {
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