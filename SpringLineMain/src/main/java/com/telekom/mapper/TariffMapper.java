package com.telekom.mapper;


import com.telekom.model.entity.Option;
import com.telekom.model.entity.Tariff;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.TariffDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TariffMapper {

    public TariffDto entityToDto(Tariff t, boolean valid) {
        TariffDto tmp = entityToDtoWithoutOptions(t);
        if (!t.getOptions().isEmpty()) {
            Set<OptionDto> tmpOptions = new HashSet<>();
            for (Option o : t.getOptions()) {
                boolean flag = true;
                if (valid && !o.isValid()) {
                    flag = false;
                }
                if (flag) {
                    OptionDto tmp2 = new OptionDto();
                    tmp2.setName(o.getName());
                    tmp2.setId(o.getId());
                    tmp2.setPriceMonthly(o.getPriceMonthly());
                    tmp2.setPriceOneTime(o.getPriceOneTime());
                    tmp2.setDescription(o.getDescription());
                    tmpOptions.add(tmp2);
                }
            }
            tmp.setOptions(tmpOptions);
        }
        return tmp;
    }

    public TariffDto entityToDtoWithoutOptions(Tariff t) {
        TariffDto tmp = new TariffDto();
        tmp.setId(t.getId());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        tmp.setPrice(t.getPrice());
        tmp.setIsValid(t.isIsValid());
        tmp.setPromoted(t.isPromoted());
        return tmp;
    }

    public Tariff dtoToEntity(TariffDto t) {
        Tariff tmp = new Tariff();
        tmp.setIsValid(t.isIsValid());
        tmp.setId(t.getId());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        tmp.setPrice(t.getPrice());
        tmp.setPromoted(t.isPromoted());
        return tmp;
    }


}
