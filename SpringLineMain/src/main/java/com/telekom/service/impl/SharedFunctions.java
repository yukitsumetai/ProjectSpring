package com.telekom.service.impl;



import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.SharedDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SharedFunctions<T> extends PaginationImpl {
    void setOptions(SharedDto sharedDto, List<Integer> id) {
        Set<OptionDto> options = new HashSet<>();
        if (id != null) {
            for (Integer i : id) {
                OptionDto t = new OptionDto();
                t.setId(i);
                options.add(t);
            }
            sharedDto.setOptions(options);
        } else sharedDto.setOptions(new HashSet<>());

    }
}
