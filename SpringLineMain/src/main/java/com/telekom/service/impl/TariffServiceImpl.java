package com.telekom.service.impl;

import com.telekom.dao.api.OptionDao;
import com.telekom.dao.api.TariffDao;
import com.telekom.model.entity.Option;
import com.telekom.model.entity.Tariff;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.Page;
import com.telekom.model.dto.TariffDto;
import com.telekom.mapper.TariffMapper;
import com.telekom.service.api.MessageProducer;
import com.telekom.service.api.TariffService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class TariffServiceImpl extends PaginationImpl<TariffDto> implements TariffService {


    @Autowired
    private TariffDao tariffDao;
    @Autowired
    private OptionDao optionDao;
    @Autowired
    private TariffMapper tariffMapper;
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private Logger logger;


    private List<TariffDto> listEntityToDto(List<Tariff> tariffs){
        List<TariffDto> tariffsDTO = new ArrayList<>();
        for (Tariff t : tariffs) {

            tariffsDTO.add(tariffMapper.entityToDto(t));
        }
        return tariffsDTO;
    }

    @Override
    @Transactional
    public List<TariffDto> getAllPromoted() {

        return  listEntityToDto(tariffDao.getAllPromoted());
    }


    @Override
    @Transactional
    public List<TariffDto> getAllValid() {
        List<Tariff> tariffs = tariffDao.getAllValid();
        return listEntityToDto( tariffs);
    }

    @Override
    public void setOptions(TariffDto tariff, List<Integer> id) {
        Set<OptionDto> options = new HashSet<>();
        if (id != null) {
            logger.info("Setting options");
            for (Integer i : id) {
                OptionDto t = new OptionDto();
                t.setId(i);
                options.add(t);
            }
            tariff.setOptions(options);
        }
        else tariff.setOptions(new HashSet<>());
    }

    @Override
    @Transactional
    public TariffDto getOne(int id) {
        logger.info("Searching tariff "+id);
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
            messageProducer.sendMessage();
    }

    @Override
    public void notify(TariffDto tariff, boolean state) {
        if(state!=tariff.isPromoted()){
            messageProducer.sendMessage();
        }
    }

    @Override
    public void notify(TariffDto tariff) {
        if(tariff.isPromoted()){
            messageProducer.sendMessage();
        }
    }

    @Override
    @Transactional
    public void editTariff(TariffDto t) {
        logger.info("Edditing tariff "+t.getId());
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
        logger.info("Deleting tariff "+id);
        Tariff tariff = tariffDao.getOne(id);
        tariff.setIsValid(false);
        tariff.setPromoted(false);
    }

    @Override
    @Transactional
    public Page<TariffDto> getPage(Integer size, Integer page) {
        logger.info("Geting tariffs");
        List<TariffDto> pageGroups =listEntityToDto(tariffDao.getPages(size, page));
        Long total=tariffDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<TariffDto> getPageValid(Integer size, Integer page) {
        logger.info("Geting valid tariffs");
        List<TariffDto> pageGroups =listEntityToDto(tariffDao.getPagesValid(size, page));
        Long total=tariffDao.getPagesValidCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    @Transactional
    public Page<TariffDto> getPage(Integer size, Integer page, Integer optionId) {
        logger.info("Geting tariffs for option");
        List<TariffDto> pageGroups =listEntityToDto(tariffDao.getPages(size, page));
        Set<Tariff> existing =optionDao.getOne(optionId).getCompatibleTariffs();
        for (Tariff i:existing) {
            for (TariffDto o:pageGroups
            ) {
                if(o.getId()==i.getId()) o.setExisting(true);
            }
        }
        Long total=tariffDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }


}

