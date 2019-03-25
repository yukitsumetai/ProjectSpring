package com.telekom.service;


import com.telekom.dao.OptionDao;
import com.telekom.dao.OptionGroupDao;
import com.telekom.dao.TariffDao;
import com.telekom.entity.Option;
import com.telekom.entity.OptionGroup;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.OptionGroupDTO;
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
    private OptionGroupDao optionGroupDao;
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
    public List<OptionDTO> getAllValid() {

        List<Option> options = optionDao.getAllValid();
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
    public void SetCompatibleTariffs(OptionDTO option, List<Integer> id) {
        Set<TariffDTO> tariffs = new HashSet<>();
        if(id!=null){
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
        if(id!=null){
        Set<OptionDTO> children = new HashSet<>();
        for (Integer i : id) {
            OptionDTO o = new OptionDTO();
            o.setId(i);
            children.add(o);
        }
        option.setChildren(children);}
    }

    @Override
    public void SetOptionGroup(OptionDTO option, Integer groupId) {
        if(groupId!= null){
            if(groupId>0){
                OptionGroupDTO og=new OptionGroupDTO();
                og.setId(groupId);
                option.setOptionGroup(og);
            }}
        else option.setOptionGroup(null);
    }

    @Override
    public void SetParent(OptionDTO option, Integer id){
        if(id!=null){
            OptionDTO o = new OptionDTO();
            o.setId(id);
            option.setParent(o);}
        else option.setParent(null);
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

    private void updateOptionRelations(OptionDTO option, Option o) {


        //Add children if exist
        if (option.getChildren().size() > 0) {
            Set<OptionDTO> children = option.getChildren();
            for (OptionDTO child : children
            ) {
                Option tmp = optionDao.getOne(child.getId());
                if (o != tmp && tmp != null && tmp.isValid() && tmp.getParent() == null && tmp.getChildren().size() == 0) {
                    tmp.setParent(o);
                }
            }
        }
        //Remove current tariffs
        if (o.getCompatibleTariffs().size() > 0) {
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
                if (tmp != null && tmp.isIsValid()) {
                    tmp.addOption(o);
                }
            }
        }


    }

    private void updateOptionParent(OptionDTO option, Option o) {
        //Set parent if exist
        OptionDTO p = option.getParent();
        if (p != null) {
            Option tmp = optionDao.getOne(p.getId());
            if (o != tmp && tmp != null && tmp.isValid() && tmp.getParent() == null) {
                o.setParent(tmp);
            }
        }
        else o.setParent(null);
    }

    private void updateOptionGroup(OptionDTO option, Option o) {
        //Set group if exist
        OptionGroupDTO p = option.getOptionGroup();
        if (p != null) {
            OptionGroup tmp = optionGroupDao.getOne(p.getId());
            if(tmp != null) {
                if (tmp.isValid()) {
                    o.setGroup(tmp);
                }
            }
        }
        else o.setGroup(null);
    }

    @Override
    @Transactional
    public void add(OptionDTO option) {
        Option o = optionMapper.DtoToEntity(option);
        updateOptionParent(option, o);
        if (o.getParent()==null){
        updateOptionGroup(option, o);}
//add option to DB
        optionDao.add(o);
        updateOptionRelations(option, o);

    }


    @Override
    public OptionDTO getOne(int id) {
        Option t = optionDao.getOne(id);
        return optionMapper.EntityToDto(t);
    }



    @Override
    @Transactional
    public void editOption(OptionDTO option) {
        Option o = optionDao.getOne(option.getId());
        o.setValid(option.isIsValid());
        //Remove children if exist
        if (o.getChildren().size() > 0) {
            Set<Option> children = o.getChildren();
            for (Option child : children
            ) {
                Option tmp = optionDao.getOne(child.getId());
                tmp.setParent(null);
            }
        }
        updateOptionParent(option, o);
        if(o.getParent()==null){
            updateOptionGroup(option, o);
        }
        else o.setGroup(null);
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
        option.setParent(null);
        option.setGroup(null);
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