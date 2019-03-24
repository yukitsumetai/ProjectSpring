package com.telekom.mapper;


import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.TariffDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TariffMapper {

    public TariffDTO EntityToDto(Tariff t) {
        TariffDTO tmp = EntityToDtoWithoutOptions(t);
        if (t.getOptions().size()>0) {
            Set<Option> options = t.getOptions();
            Set<OptionDTO> tmpOptions = new HashSet<>();
            for (Option o : options) {
                OptionDTO tmp2 = new OptionDTO();
                tmp2.setName(o.getName());
                tmp2.setId(o.getId());
                tmp2.setPriceMonthly(o.getPriceMonthly());
                tmp2.setPriceOneTime(o.getPriceOneTime());
                tmp2.setDescription(o.getDescription());
                tmpOptions.add(tmp2);
            }
            tmp.setOptions(tmpOptions);
        }
        return tmp;
    }

    public TariffDTO EntityToDtoWithoutOptions(Tariff t) {
        TariffDTO tmp = new TariffDTO();
        tmp.setId(t.getId());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        tmp.setPrice(t.getPrice());
        tmp.setIsValid(t.isIsValid());
        return tmp;
    }

    public Tariff DtoToEntity(TariffDTO t) {
        Tariff tmp = new Tariff();
        tmp.setIsValid(t.isIsValid());
        tmp.setId(t.getId());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        tmp.setPrice(t.getPrice());
        return tmp;
    }

}
