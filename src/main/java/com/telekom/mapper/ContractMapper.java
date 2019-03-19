package com.telekom.mapper;

import com.telekom.entity.Contract;
import com.telekom.entity.Option;
import com.telekom.entityDTO.ContractDTO;
import com.telekom.entityDTO.OptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
        BigInteger number = new BigInteger(c.getPhoneNumber());
       tmp.setPhoneNumber(number);
        tmp.setPrice(c.getPrice());
        tmp.setTariff(tariffMapper.DtoToEntity(c.getTariff()));
        tmp.setPriceTariff(c.getTariff().getPrice());
        if (c.getOptions() != null) {
            List<Option> options = new ArrayList<>();
            for (OptionDTO o :
                    c.getOptions()) {
                options.add(optionMapper.DtoToEntity(o));
            }
            tmp.setOptions(options);
        }
        return tmp;
    }


    public ContractDTO EntityToDto(Contract c){
        ContractDTO tmp=new ContractDTO();

         tmp.setPhoneNumber(c.getPhoneNumber().toString());
        tmp.setPrice(c.getPrice());
        tmp.setTariff(tariffMapper.EntityToDtoWithoutOptions(c.getTariff()));
        tmp.setClient(clientMapper.EntityToDto(c.getClient()));
        tmp.setPhoneNumber(c.getPhoneNumber().toString());

        if (c.getOptions() != null) {
            List<OptionDTO> options = new ArrayList<>();
            for (Option o :
                    c.getOptions()) {
                options.add(optionMapper.EntityToDtoWithoutTariff(o));
            }
            tmp.setOptions(options);
        }
        return tmp;


    }
}
