package com.telekom.mapper;

import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.TariffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        Option o = t.getParent();
        if (o != null) {
            tmp.setParent(EntityToDtoWithoutTariff(o));
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

        return tmp;
    }
}
