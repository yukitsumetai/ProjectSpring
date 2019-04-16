package com.telekom.mapper;

import com.telekom.model.entity.Contract;
import com.telekom.model.entity.Option;
import com.telekom.model.dto.ContractDto;
import com.telekom.model.dto.OptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ContractMapper {

    @Autowired
    ClientMapper clientMapper;

    @Autowired
    TariffMapper tariffMapper;

    @Autowired
    OptionMapper optionMapper;

    public Contract dtoToEntity(ContractDto c) {
        Contract tmp = new Contract();
        tmp.setPhoneNumber(c.getPhoneNumberInt());
        tmp.setPrice(c.getPrice());
        tmp.setBlocked(c.isBlocked());
        tmp.setAgentBlock(c.isAgentBlock());
        tmp.setTariff(tariffMapper.dtoToEntity(c.getTariff()));
        if (c.getOptions() != null) {
            Set<Option> options = new HashSet<>();
            for (OptionDto o :
                    c.getOptions()) {
                options.add(optionMapper.dtoToEntity(o));
            }
            tmp.setOptions(options);
        }
        return tmp;
    }


    public ContractDto entityToDto(Contract c) {
        ContractDto tmp = new ContractDto();

        tmp.setPhoneNumber(c.getPhoneNumber().toString());
        tmp.setPrice(c.getPrice());
        tmp.setTariff(tariffMapper.entityToDtoWithoutOptions(c.getTariff()));
        tmp.setClient(clientMapper.entityToDto(c.getClient()));
        tmp.setPhoneNumber(c.getPhoneNumber().toString());
        tmp.setBlocked(c.isBlocked());
        tmp.setAgentBlock(c.isAgentBlock());
        if (!c.getOptions().isEmpty()) {
            Set<OptionDto> options = new HashSet<>();
            for (Option o :
                    c.getOptions()) {
                options.add(optionMapper.entityToDtoWithoutTariff(o));
            }
            tmp.setOptions(options);
        }
        return tmp;
    }



}
