package com.telekom.service;

import com.telekom.entityDTO.Page;

import java.util.List;

public class PaginationImpl<T>  {

    public Page<T> getPageDraft(List<T> data, Long total2, Integer page, Integer size) {
        Long total=total2;
        Double d = Math.ceil((double) total / (double) size);
        Double lastPage=(total+size)-(d*size);
        Page<T> page2 = new Page<T>(data,  page, d.intValue(), lastPage.intValue());
        return page2;
    }
}
