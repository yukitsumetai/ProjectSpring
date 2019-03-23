package com.telekom.service;


import com.telekom.dao.OptionDao;
import com.telekom.dao.TariffDao;
import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.OptionDTO;
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
public class OptionServiceImpl implements OptionService {


    @Autowired
    private OptionDao optionDao;
    @Autowired
    private TariffDao tariffDao;
    @Autowired
    private OptionMapper optionMapper;

    List<OptionDTO> listEntityToDto(List<Option> options) {
        List<OptionDTO> OptionsDTO = new ArrayList<>();
        for (Option t : options) {
            OptionsDTO.add(optionMapper.EntityToDto(t));
        }
        return OptionsDTO;

    }

    @Override
    public List<OptionDTO> getAll() {

        List<Option> options = optionDao.getAll();
        return listEntityToDto(options);
    }

    @Override
    public List<OptionDTO> getAllNoParent() {
        List<Option> options = optionDao.getAllValidNoParent();
        return listEntityToDto(options);
    }

    @Override
    public List<OptionDTO> getAllNoChildrenAndParent() {
        List<Option> options = optionDao.getAllValidNoChildrenAndParent();
        return listEntityToDto(options);
    }

    @Override
    public Set<OptionDTO> findByTariff(Integer id) {

        List<Option> Options = optionDao.findByTariff(id);
        Set<OptionDTO> OptionsDTO = new HashSet<>();
        for (Option t : Options) {
            OptionsDTO.add(optionMapper.EntityToDto(t));
        }
        return OptionsDTO;
    }

    @Override
    @Transactional
    public void add(OptionDTO option) {
        Option o = optionMapper.DtoToEntity(option);
//Set parent if exist
        OptionDTO p = option.getParent();
        if (p != null) {
            Option tmp = optionDao.getOne(p.getId());
            if (tmp != null && tmp.isValid() && tmp.getParent() == null) {
                o.setParent(tmp);
            }
        }

//add option to DB
        optionDao.add(o);
//Add children if exist
        if (option.getChildren().size() > 0) {
            Set<OptionDTO> children = option.getChildren();
            for (OptionDTO child : children
            ) {
                Option tmp = optionDao.getOne(child.getId());
                if (tmp != null && tmp.isValid() && tmp.getParent() == null && tmp.getChildren().size() == 0) {
                    tmp.setParent(o);
                }
            }
        }
//Add tariffs if exist
        if (option.getCompatibleTariffs().size() > 0) {
            Set<TariffDTO> tariffs = option.getCompatibleTariffs();
            for (TariffDTO t : tariffs
            ) {
                Tariff tmp = tariffDao.getOne(t.getId());
                if (tmp != null && tmp.isIsValid()) {
                    //o.addTariff(tmp);
                    tmp.addOption(o);
                }
            }
        }

    }


    @Override
    public OptionDTO getOne(int id) {
        Option t = optionDao.getOne(id);
        return optionMapper.EntityToDto(t);
    }

    @Override
    @Transactional
    public void editOption(OptionDTO o) {
        Option option = optionDao.getOne(o.getId());
        option.setValid(o.isIsValid());
        option.setDescription(o.getDescription());
    }

    @Override
    @Transactional
    public void deleteOption(Integer id) {
        Option option = optionDao.getOne(id);
        option.setParent(null);
        if (option.getCompatibleTariffs().size() > 0) {
            Set<Tariff> tariffs = option.getCompatibleTariffs();
            for (Tariff t : tariffs) {
                t.removeOption(option);
            }
        }
        if (option.getChildren().size() > 0) {
            Set<Option> children = option.getChildren();
            for (Option t : children) {
                t.setParent(null);
            }
        }
        option.setChildren(new HashSet<>());
        option.setValid(false);
    }


}