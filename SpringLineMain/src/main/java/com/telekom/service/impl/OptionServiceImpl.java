package com.telekom.service.impl;


import com.telekom.dao.api.OptionDao;
import com.telekom.dao.api.OptionGroupDao;
import com.telekom.dao.api.TariffDao;
import com.telekom.model.entity.Option;
import com.telekom.model.entity.OptionGroup;
import com.telekom.model.entity.Tariff;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.OptionGroupDto;
import com.telekom.model.dto.Page;
import com.telekom.model.dto.TariffDto;
import com.telekom.mapper.OptionMapper;
import com.telekom.service.api.OptionService;
import org.apache.log4j.Logger;
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
    @Autowired
    private Logger logger;

    private List<OptionDto> listEntityToDto(List<Option> options) {

        List<OptionDto> optionsDto = new ArrayList<>();
        if (options == null) return optionsDto;
        for (Option t : options) {
            optionsDto.add(optionMapper.entityToDto(t));
        }
        return optionsDto;

    }

    @Override
    @Transactional
    public Page<OptionDto> getOptions(Integer size, Integer page) {
        logger.info("Getting options, page " + page);
        List<OptionDto> pageGroups = listEntityToDto(optionDao.getPages(size, page));
        Long total = optionDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<OptionDto> getOptionsForTariff(Integer size, Integer page, Integer tariffId) {
        logger.info("Getting options for tariff, page " + page + ", tariff id " + tariffId);
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
    public Page<OptionDto> getOptionsForNewOption(Integer size, Integer page, boolean parent) {
        logger.info("Getting options for new option, page " + page + ", look for parent: " + parent);
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
        logger.info("Getting options for existing option, page " + page + ", option id" + optionId + ", look for parent: " + parent);
        List<OptionDto> optionPage;
        Option optionExisting = optionDao.getOne(optionId);
        Long total;
        if (parent) {
            optionPage = getParentForExisting(size, page, optionExisting);
            total = optionDao.getPagesCountNoParent();
        } else {
            optionPage = getChildrenForExisting(size, page, optionExisting);
            total = optionDao.getPagesNoChildrenAndParentExisting(optionId);
        }
        optionPage.removeIf(st -> st.getId() == optionExisting.getId());
        return getPageDraft(optionPage, total, page, size);
    }

    private List<OptionDto> getParentForExisting(Integer size, Integer page, Option o) {
        logger.info("Looking for parent");
        List<OptionDto> optionPage = listEntityToDto(optionDao.getAllNoParent(size, page));
        Option parent2 = o.getParent();
        if (parent2 != null) {
            for (OptionDto o2 : optionPage
            ) {
                if (o.getParent().getId() == o2.getId()) o2.setExisting(true);
                if (o.getId() == o2.getId()) o2.setExisting(true);
            }
        }
        return optionPage;
    }


    private List<OptionDto> getChildrenForExisting(Integer size, Integer page, Option o) {
        logger.info("Looking for children");
        List<OptionDto> optionPage = listEntityToDto(optionDao.getAllNoChildrenAndParentExisting(size, page, o.getId()));
        for (OptionDto o2 : optionPage) {
            for (Option c : o.getChildren()) {
                if (c.getId() == o2.getId()) {
                    o2.setExisting(true);
                }
            }
        }
        return optionPage;
    }

    @Override
    @Transactional
    public Page<OptionDto> getOpionsForOptionGroup(Integer size, Integer page) {
        logger.info("Getting options for new option group, page " + page);
        List<OptionDto> pageGroups = listEntityToDto(optionDao.getAllNoParentNoGroup(size, page));
        Long total = optionDao.getPagesCountNoParentNoGroup();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<OptionDto> getOptionsForExistingOptionGroup(Integer size, Integer page, Integer optionGroupId) {
        logger.info("Getting options for existing option group, page " + page);
        OptionGroup og = optionGroupDao.getOne(optionGroupId);
        List<OptionDto> pageGroups = listEntityToDto(optionDao.getAllNoParentNoGroupExisting(size, page, optionGroupId));
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
        logger.info("Setting compatible tariffs for option " + option);
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
        logger.info("Setting children for option " + option);
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
        logger.info("Setting option group for option " + option);
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
        logger.info("Setting parent for option " + option);
        if (id != null) {
            OptionDto o = new OptionDto();
            o.setId(id);
            option.setParent(o);
        } else option.setParent(null);
    }

    @Override
    public Set<OptionDto> findByTariff(Integer id) {
        logger.info("Searching for options by tariff id " + id);
        List<Option> options = optionDao.findByTariffParents(id); //valid only
        Set<OptionDto> optionsDto = new HashSet<>();
        for (Option t : options) {
            optionsDto.add(optionMapper.entityToDto(t));
        }
        return optionsDto;
    }

    @Override
    public Set<OptionDto> findByTariffChildren(Integer id) {
        logger.info("Searching for children options by tariff id " + id);
        List<Option> options = optionDao.findByTariffChildren(id);
        Set<OptionDto> optionsDto = new HashSet<>();
        for (Option t : options) {
            optionsDto.add(optionMapper.entityToDto(t));
        }
        return optionsDto;
    }

    public void updateTariff(OptionDto optionDto, Option option) {
        if (!option.getCompatibleTariffs().isEmpty()) {
            logger.info("Removing current tariffs");
            Set<Tariff> tariffs = option.getCompatibleTariffs();
            for (Tariff t : tariffs
            ) {
                Tariff tmp = tariffDao.getOne(t.getId());
                tmp.removeOption(option);
            }
        }
//Add tariffs if exist

        if (!optionDto.getCompatibleTariffs().isEmpty()) {
            logger.info("Adding tariffs");
            Set<TariffDto> tariffs = optionDto.getCompatibleTariffs();
            for (TariffDto t : tariffs
            ) {
                Tariff tmp = tariffDao.getOne(t.getId());
               tmp.addOption(option);
            }
        }
    }

    public void updateChildren(OptionDto optionDto, Option option) {

        if (!option.getChildren().isEmpty()) {
            logger.info("Removing current children");
            Set<Option> children = option.getChildren();
            for (Option child : children
            ) {
                Option tmp = optionDao.getOne(child.getId());
                tmp.setParent(null);
                tmp.setGroup(null);
            }
        }
        //Add children if exist
        if (option.getParent() == null) {
            if (!optionDto.getChildren().isEmpty()) {
                logger.info("Adding children");
                Set<OptionDto> children = optionDto.getChildren();
                for (OptionDto child : children
                ) {
                    Option tmp = optionDao.getOne(child.getId());
                    if (option != tmp && tmp.getParent() == null && tmp.getChildren().isEmpty()) {
                        tmp.setParent(option);
                        tmp.setGroup(null);
                    }
                }
            }
        }
    }

    public void updateOptionParent(OptionDto option, Option o) {
        /*
        OptionDto p = option.getParent();
        if (p != null) {
            logger.info("Setting parent");
            Option tmp = optionDao.getOne(p.getId());
            if (o != tmp && tmp.getParent() == null) {
                o.setParent(tmp);
            }
        } else o.setParent(null);*/
    }

    public void updateOptionGroup(OptionDto option, Option o) {
        if (o.getParent() == null) {
            logger.info("Updating option group");
            OptionGroupDto p = option.getOptionGroup();
            if (p != null) {
                OptionGroup tmp = optionGroupDao.getOne(p.getId());
                if (tmp != null) { //dummy group - 0
                    o.setGroup(tmp);
                }
            } else o.setGroup(null);
        } else o.setGroup(null);
    }

    @Override
    @Transactional
    public void add(OptionDto option) {
        Option o = optionMapper.dtoToEntity(option);
        updateOptionParent(option, o);
        updateOptionGroup(option, o);
        logger.info("Adding option to DB");
        optionDao.add(o);
        updateTariff(option, o);
        updateChildren(option, o);
    }


    @Override
    public OptionDto getOne(int id) {
        Option t = optionDao.getOne(id);
        if (t != null) return optionMapper.entityToDto(t);
        else return null;
    }


    @Override
    @Transactional
    public void editOption(OptionDto option) {
        logger.info("Editing option " + option.getId());
        Option o = optionDao.getOne(option.getId());
        o.setValid(option.isIsValid());
        updateOptionParent(option, o);
        updateOptionGroup(option, o);
       o.setDescription(option.getDescription());
        updateTariff(option, o);
        updateChildren(option, o);
    }

    @Override
    public void removeRelations(OptionDto option) {
        logger.info("Removing relations " + option.getId());
        option.setParent(null);
        option.setChildren(new HashSet<>());
    }

    @Override
    @Transactional
    public void deleteOption(Integer id) {
        logger.info("Deleting options " + id);
        Option option = optionDao.getOne(id);
        if(option!=null) {
            option.setValid(false);
        }
    }
}