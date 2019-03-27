package com.telekom.mapper;

import com.telekom.entity.Contract;
import com.telekom.entity.Option;
import com.telekom.entityDTO.ContractDTO;
import com.telekom.entityDTO.OptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ContractMapper {

    @Autowired
    ClientMapper clientMapper;

    @Autowired
    TariffMapper tariffMapper;

    @Autowired
    OptionMapper optionMapper;

    public Contract DtoToEntity(ContractDTO c) {
        Contract tmp = new Contract();
        tmp.setPhoneNumber(c.getPhoneNumberInt());
        tmp.setPrice(c.getPrice());
        tmp.setBlocked(c.isBlocked());
        tmp.setAgentBlock(c.isAgentBlock());
        tmp.setTariff(tariffMapper.DtoToEntity(c.getTariff()));
        if (c.getOptions() != null) {
            Set<Option> options = new HashSet<>();
            for (OptionDTO o :
                    c.getOptions()) {
                options.add(optionMapper.DtoToEntity(o));
            }
            tmp.setOptions(options);
        }
        return tmp;
    }


    public ContractDTO EntityToDto(Contract c) {
        ContractDTO tmp = new ContractDTO();

        tmp.setPhoneNumber(c.getPhoneNumber().toString());
        tmp.setPrice(c.getPrice());
        tmp.setTariff(tariffMapper.EntityToDtoWithoutOptions(c.getTariff()));
        tmp.setClient(clientMapper.EntityToDto(c.getClient()));
        tmp.setPhoneNumber(c.getPhoneNumber().toString());
        tmp.setBlocked(c.isBlocked());
        tmp.setAgentBlock(c.isAgentBlock());
        if (c.getOptions().size()> 0) {
            Set<OptionDTO> options = new HashSet<>();
            for (Option o :
                    c.getOptions()) {
                options.add(optionMapper.EntityToDtoWithoutTariff(o));
            }
            tmp.setOptions(options);
        }
        return tmp;
    }



}
