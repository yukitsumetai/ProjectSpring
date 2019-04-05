package com.telekom.mapper;

import com.telekom.entity.Option;
import com.telekom.entity.OptionGroup;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.OptionGroupDTO;
import com.telekom.entityDTO.TariffDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OptionMapper {

    public OptionDTO EntityToDto(Option t) {

        OptionDTO tmp = EntityToDtoWithoutTariff(t);
        if (t.getCompatibleTariffs() != null) {
            Set<Tariff> tariffs = t.getCompatibleTariffs();
            Set<TariffDTO> tariffsDTO = new HashSet<>();
            for (Tariff tn : tariffs) {
                tmp.addTariff(tn.getId(), tn.getName());
            }
        }
        return tmp;
    }

    public OptionDTO EntityToDtoWithoutTariff(Option t) {
        OptionDTO tmp = new OptionDTO();
        tmp.setIsValid(t.isValid());
        OptionGroup g = t.getGroup();
        if (g != null) {
            OptionGroupDTO og= new OptionGroupDTO();
            og.setName(g.getName());
            og.setId(g.getId());
            tmp.setOptionGroup(og);
        }
        Option o = t.getParent();
        if (o != null) {
            OptionDTO parent = new OptionDTO(o.getId(), o.getName());
            tmp.setParent(parent);
        }
        if (t.getChildren().size() > 0) {
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

    public Option DtoToEntity(OptionDTO t) {
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
