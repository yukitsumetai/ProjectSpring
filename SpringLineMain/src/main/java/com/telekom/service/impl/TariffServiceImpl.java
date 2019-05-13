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
public class TariffServiceImpl extends SharedFunctions implements TariffService {


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

    /**
     * This function maps list of tariffs to list of tariffs Dto
     *
     * @param tariffs
     * @return list of tariffs Dto mapped from tariffs
     */
    private List<TariffDto> listEntityToDto(List<Tariff> tariffs) {
        List<TariffDto> tariffsDTO = new ArrayList<>();
        if (tariffs == null) return tariffsDTO;
        for (Tariff t : tariffs) {
            tariffsDTO.add(tariffMapper.entityToDto(t));
        }
        return tariffsDTO;
    }

    /**
     * Returns a page of tariff Dto for table
     *
     * @param size page size
     * @param page number of page
     * @return page of tariff Dto with list of tariff Dto and page parameters
     */
    @Override
    @Transactional
    public Page<TariffDto> getPage(Integer size, Integer page) {
        logger.info("Getting tariffs");

        List<TariffDto> pageGroups = listEntityToDto(tariffDao.getPages(size, page));
        Long total = tariffDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    /**
     * Returns a page of only valid tariff Dto for table
     *
     * @param size
     * @param page
     * @return page of tariff Dto with list of tariff Dto and page parameters
     */
    @Override
    @Transactional
    public Page<TariffDto> getValidPaginated(Integer size, Integer page) {
        logger.info("Getting valid tariffs");
        List<TariffDto> pageGroups = listEntityToDto(tariffDao.getPagesValid(size, page));
        Long total = tariffDao.getPagesValidCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    /**
     * Returns a page of tariff Dto for table for a giving Option
     * marking which tariffs are already compatible with the option
     *
     * @param size
     * @param page
     * @param optionId
     * @return page of tariff Dto with list of tariff Dto and page parameters
     */
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

    /**
     * Returns all promoted tariffs tp show on advertisment stand
     * Option details are not returned
     * @return list of promoted tariffs Dto
     */
    @Override
    @Transactional
    public List<TariffDto> getAllPromoted() {
        List<Tariff> tariffs = tariffDao.getAllPromoted();
        List<TariffDto> tariffsDTO = new ArrayList<>();
        if (tariffs == null) return tariffsDTO;
        for (Tariff t : tariffs) {

            tariffsDTO.add(tariffMapper.entityToDtoWithoutOptions(t));
        }
        return tariffsDTO;
    }

    /**
     * Returns all valid tariffs
     *
     * @return list of valid tariffs Dto
     */
    @Override
    @Transactional
    public List<TariffDto> getAllValid() {
        List<Tariff> tariffs = tariffDao.getAllValid();
        return listEntityToDto(tariffs);
    }

    /**
     * Set compatible options to tariff Dto
     *
     * @param tariff
     * @param id     list of ids of compatible options
     */
    @Override
    public void setOptionsDto(TariffDto tariff, List<Integer> id) {
        setOptions(tariff, id);
    }

    /**
     * Return tariff Dto by id
     *
     * @param id of tariff
     * @return tariff dto
     */
    @Override
    @Transactional
    public TariffDto getTariff(int id) {
        logger.info("Searching tariff " + id);
        Tariff t = tariffDao.getOne(id);
        return tariffMapper.entityToDto(t);
    }

    /**
     * Set options to tariff in DB
     *
     * @param tariff tariff Dto
     * @param t      corresponding tariff in DB
     */
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

    /**
     * Add tariff to DB
     *
     * @param tariff tariff Dto
     */
    @Override
    @Transactional
    public void add(TariffDto tariff) {
        Tariff t = tariffMapper.dtoToEntity(tariff);
        addOptions(tariff, t);
        logger.info("Adding new tariff");
        tariffDao.add(t);
    }

    /**
     * send message if tariff was deleted
     */
    @Override
    public void notifyDeleted() {
        jmsService.sendMessage();
    }

    /**
     * Send message if promotion state of tariff was changed
     *
     * @param tariff
     * @param state  previous state ow the tariff
     */
    @Override
    public void notify(TariffDto tariff, boolean state) {
        if (state != tariff.isPromoted()) {
            jmsService.sendMessage();
        }
    }

    /**
     * send message if new tariff was promoted
     *
     * @param tariff
     */
    @Override
    public void notify(TariffDto tariff) {
        if (tariff.isPromoted()) {
            jmsService.sendMessage();
        }
    }

    /**
     * Edit existing tariff in DB
     *
     * @param t tariff Dto
     */
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

    /**
     * Sets tarif state to invalid and not promoted
     *
     * @param id of tariff
     */
    @Override
    @Transactional
    public void deleteTariff(Integer id) {
        logger.info("Deleting tariff " + id);
        Tariff tariff = tariffDao.getOne(id);
        tariff.setIsValid(false);
        tariff.setPromoted(false);
    }

}

