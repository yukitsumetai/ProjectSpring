package com.telekom.mapper;

import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.TariffDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OptionMapper {
    public OptionDTO EntityToDto(Option t){

        OptionDTO tmp=EntityToDtoWithoutTariff(t);
        if(t.getCompatibleTariffs()!=null){
        List<Tariff> tariffs=t.getCompatibleTariffs();
        List<TariffDTO> tariffsDTO=new ArrayList();
        for (Tariff tn:tariffs
        ) {
            TariffDTO tmp2=new TariffDTO();
            tmp2.setName(tn.getName());
            tmp2.setId(tn.getId());
            tariffsDTO.add(tmp2);
        }
         tmp.setCompatibleTariffs(tariffsDTO);}
        return tmp;
    }
    public OptionDTO EntityToDtoWithoutTariff(Option t){
        OptionDTO tmp=new OptionDTO();
        tmp.setIsValid(t.isValid());
        tmp.setId(t.getId());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        tmp.setPriceMonthly(t.getPriceMonthly());
        tmp.setPriceOneTime(t.getPriceOneTime());
        return tmp;
    }

    public Option DtoToEntity(OptionDTO t){
        Option tmp=new Option();
        tmp.setId(t.getId());
        tmp.setValid(t.isIsValid());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        tmp.setPriceMonthly(t.getPriceMonthly());

        return tmp;
    }
}
