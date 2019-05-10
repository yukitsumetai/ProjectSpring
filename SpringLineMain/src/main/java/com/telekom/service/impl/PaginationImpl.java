package com.telekom.service.impl;

import com.telekom.model.dto.Page;
import java.util.List;


public abstract class PaginationImpl<T> {

    public Page<T> getPageDraft(List<T> data, Long total2, Integer page, Integer size) {
        Double d = Math.ceil((double) total2 / (double) size);
        Double lastPage = (total2 + size) - (d * size);
        return new Page<>(data, page, d.intValue(), lastPage.intValue());
    }



}
