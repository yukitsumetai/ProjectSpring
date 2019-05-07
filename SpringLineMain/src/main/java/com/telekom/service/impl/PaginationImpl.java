package com.telekom.service.impl;

import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.OptionGroupDto;
import com.telekom.model.dto.Page;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PaginationImpl<T> {

    public Page<T> getPageDraft(List<T> data, Long total2, Integer page, Integer size) {
        Double d = Math.ceil((double) total2 / (double) size);
        Double lastPage = (total2 + size) - (d * size);
        return new Page<>(data, page, d.intValue(), lastPage.intValue());
    }



}
