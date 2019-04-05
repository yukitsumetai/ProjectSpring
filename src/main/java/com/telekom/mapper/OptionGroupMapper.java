package com.telekom.mapper;

import com.telekom.entity.Option;
import com.telekom.entity.OptionGroup;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.OptionGroupDTO;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OptionGroupMapper {
    public OptionGroupDTO EntityToDto(OptionGroup t) {
        OptionGroupDTO tmp = new OptionGroupDTO();
        tmp.setId(t.getId());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        tmp.setIsValid(t.isValid());
        if (!t.getOptions().isEmpty()) {
            Set<Option> options = t.getOptions();
            for (Option o : options) {
                OptionDTO tmp2 = new OptionDTO();
                tmp2.setName(o.getName());
                tmp2.setId(o.getId());
                tmp.addOption(tmp2);
            }
        }
        return tmp;
    }

    public OptionGroup DtoToEntity(OptionGroupDTO t){
        OptionGroup tmp=new OptionGroup();
        tmp.setValid(t.isIsValid());
        tmp.setId(t.getId());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        return tmp;
    }


}
