package com.telekom.mapper;

import com.telekom.model.entity.Option;
import com.telekom.model.entity.OptionGroup;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.OptionGroupDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OptionGroupMapper {
    public OptionGroupDto entityToDto(OptionGroup t) {
        OptionGroupDto tmp = new OptionGroupDto();
        tmp.setId(t.getId());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        tmp.setIsValid(t.isValid());
        if (!t.getOptions().isEmpty()) {
            Set<Option> options = t.getOptions();
            for (Option o : options) {
                OptionDto tmp2 = new OptionDto();
                tmp2.setName(o.getName());
                tmp2.setId(o.getId());
                tmp.addOption(tmp2);
            }
        }
        return tmp;
    }

    public OptionGroup dtoToEntity(OptionGroupDto t){
        OptionGroup tmp=new OptionGroup();
        tmp.setValid(t.isIsValid());
        tmp.setId(t.getId());
        tmp.setDescription(t.getDescription());
        tmp.setName(t.getName());
        return tmp;
    }


}
