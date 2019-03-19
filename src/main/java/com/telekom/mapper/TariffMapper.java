package com.telekom.mapper;


import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.TariffDTO;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@Component
public class TariffMapper {

  public TariffDTO EntityToDto(Tariff t){
        TariffDTO tmp= EntityToDtoWithoutOptions(t);
      if(t.getOptions()!=null){
      List<Option> options=t.getOptions();
        List<OptionDTO> tmpOptions=new ArrayList();
        for (Option o:options) {
            OptionDTO tmp2=new OptionDTO();
            tmp2.setName(o.getName());
            tmp2.setId(o.getId());
            tmp2.setPriceMonthly(o.getPriceMonthly());
            tmp2.setPriceOneTime(o.getPriceOneTime());
            tmp2.setDescription(o.getDescription());
            tmpOptions.add(tmp2);
        }
        tmp.setOptions(tmpOptions);}
        return tmp;
  }

    public TariffDTO EntityToDtoWithoutOptions(Tariff t){
        TariffDTO tmp=new TariffDTO();
        tmp.setId(t.getId());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        tmp.setPrice(t.getPrice());
        return tmp;
    }

        public Tariff DtoToEntity(TariffDTO t){
            Tariff tmp=new Tariff();
            tmp.setId(t.getId());
            tmp.setDescription(t.getDescription());
            tmp.setName(t.getName());
            tmp.setPrice(t.getPrice());
            return tmp;
        }


}
