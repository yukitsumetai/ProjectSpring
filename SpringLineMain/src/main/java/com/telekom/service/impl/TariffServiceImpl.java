package com.telekom.service.impl;

import com.telekom.dao.api.OptionDao;
import com.telekom.dao.api.TariffDao;
import com.telekom.model.entity.Option;
import com.telekom.model.entity.Tariff;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.Page;
import com.telekom.model.dto.TariffDto;
import com.telekom.mapper.TariffMapper;
import com.telekom.service.api.JmsService;
import com.telekom.service.api.TariffService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;


@Service
public class TariffServiceImpl extends SharedFunctions<TariffDto> implements TariffService {


    @Autowired
    private TariffDao tariffDao;
    @Autowired
    private OptionDao optionDao;
    @Autowired
    private TariffMapper tariffMapper;
    @Autowired
    private JmsService jmsService;
    @Autowired
    private Logger logger;


   private List<TariffDto> listEntityToDto(List<Tariff> tariffs) {
        List<TariffDto> tariffsDTO = new ArrayList<>();
       if(tariffs==null) return tariffsDTO;
        for (Tariff t : tariffs) {

            tariffsDTO.add(tariffMapper.entityToDto(t));
        }
        return tariffsDTO;
    }

    @Override
    @Transactional
    public Page<TariffDto> getPage(Integer size, Integer page) {
        logger.info("Getting tariffs");

        List<TariffDto> pageGroups = listEntityToDto(tariffDao.getPages(size, page));
        Long total = tariffDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<TariffDto> getValidPaginated(Integer size, Integer page) {
        logger.info("Getting valid tariffs");
        List<TariffDto> pageGroups = listEntityToDto(tariffDao.getPagesValid(size, page));
        Long total = tariffDao.getPagesValidCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<TariffDto> getPage(Integer size, Integer page, Integer optionId) {
        logger.info("Getting tariffs for option");
        List<TariffDto> pageGroups = listEntityToDto(tariffDao.getPages(size, page));
        Set<Tariff> existing = optionDao.getOne(optionId).getCompatibleTariffs();
       for (Tariff i : existing) {
            for (TariffDto o : pageGroups
            ) {
                if (o.getId() == i.getId()) o.setExisting(true);
            }
        }
        Long total = tariffDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public List<TariffDto> getAllPromoted() {

        return listEntityToDto(tariffDao.getAllPromoted());
    }


    @Override
    @Transactional
    public List<TariffDto> getAllValid() {
        List<Tariff> tariffs = tariffDao.getAllValid();
        return listEntityToDto(tariffs);
    }

    @Override
    public void setOptionsDto(TariffDto tariff, List<Integer> id) {
        setOptions(tariff, id);
    }

    @Override
    @Transactional
    public TariffDto getTariff(int id) {
        logger.info("Searching tariff " + id);
        Tariff t = tariffDao.getOne(id);
        return tariffMapper.entityToDto(t);
    }

    private void addOptions(TariffDto tariff, Tariff t) {
        if (!tariff.getOptions().isEmpty()) {
            Set<OptionDto> options = tariff.getOptions();
            logger.info("Adding options to tariff");
            for (OptionDto o : options
            ) {
                Option tmp = optionDao.getOne(o.getId());
                if (tmp != null) t.addOption(tmp);
            }
        }
    }

    @Override
    @Transactional
    public void add(TariffDto tariff) {
        Tariff t = tariffMapper.dtoToEntity(tariff);
        addOptions(tariff, t);
        logger.info("Adding new tariff");
        tariffDao.add(t);
    }

    @Override
    public void notifyDeleted() {
        jmsService.sendMessage();
    }

    @Override
    public void notify(TariffDto tariff, boolean state) {
        if (state != tariff.isPromoted()) {
            jmsService.sendMessage();
        }
    }

    @Override
    public void notify(TariffDto tariff) {
        if (tariff.isPromoted()) {
            jmsService.sendMessage();
        }
    }

    @Override
    @Transactional
    public void editTariff(TariffDto t) {
        logger.info("Editing tariff " + t.getId());
        Tariff tariff = tariffDao.getOne(t.getId());
        tariff.setIsValid(t.isIsValid());
        tariff.setPromoted(t.isPromoted());
        tariff.setDescription(t.getDescription());
        tariff.setOptions(new HashSet<>());
        addOptions(t, tariff);
    }

    @Override
    @Transactional
    public void deleteTariff(Integer id) {
        logger.info("Deleting tariff " + id);
        Tariff tariff = tariffDao.getOne(id);
        tariff.setIsValid(false);
        tariff.setPromoted(false);
    }

}

