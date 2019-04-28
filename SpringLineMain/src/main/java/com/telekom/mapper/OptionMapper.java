package com.telekom.mapper;

import com.telekom.model.entity.Option;
import com.telekom.model.entity.OptionGroup;
import com.telekom.model.entity.Tariff;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.OptionGroupDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OptionMapper {

    public OptionDto entityToDto(Option t) {

        OptionDto tmp = entityToDtoWithoutTariff(t);
        if (t.getCompatibleTariffs() != null) {
            Set<Tariff> tariffs = t.getCompatibleTariffs();
            for (Tariff tn : tariffs) {
                tmp.addTariff(tn.getId(), tn.getName());
            }
        }
        return tmp;
    }

    public OptionDto entityToDtoWithoutTariff(Option t) {
        OptionDto tmp = new OptionDto();
        tmp.setIsValid(t.isValid());
        OptionGroup g = t.getGroup();
        if (g != null) {
            OptionGroupDto og= new OptionGroupDto();
            og.setName(g.getName());
            og.setId(g.getId());
            tmp.setOptionGroup(og);
        }
        Option o = t.getParent();
        if (o != null) {
            OptionDto parent = new OptionDto(o.getId(), o.getName());
            tmp.setParent(parent);
        }
        if (!t.getChildren().isEmpty()) {
            Set<Option> children = t.getChildren();
            for (Option child : children
            ) {
                tmp.addOption(child.getId(), child.getName());
            }
        }
        tmp.setId(t.getId());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        tmp.setPriceMonthly(t.getPriceMonthly());
        tmp.setPriceOneTime(t.getPriceOneTime());
        return tmp;
    }

    public Option dtoToEntity(OptionDto t) {
        Option tmp = new Option();
        tmp.setId(t.getId());
        tmp.setValid(t.isIsValid());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        tmp.setPriceMonthly(t.getPriceMonthly());
        if(t.getParent()!=null){
            Option o=new Option();
            o.setId(t.getParent().getId());
            o.setName(t.getParent().getName());
            tmp.setParent(o);
        }
        return tmp;
    }
}
