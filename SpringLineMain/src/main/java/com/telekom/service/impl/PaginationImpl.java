package com.telekom.service.impl;

import com.telekom.model.dto.Page;
import java.util.List;


public abstract class PaginationImpl<T> {

    /**
     * Template for getting a page (for example for table or dropdown)
     * @param data on each page
     * @param total total amount of pages
     * @param page number of current page
     * @param size n of objects  on each page
     * @return
     */
    public Page<T> getPageDraft(List<T> data, Long total, Integer page, Integer size) {
        Double d = Math.ceil((double) total / (double) size);
        double lastPage = (total + size) - (d * size);
        return new Page<>(data, page, d.intValue(), (int) lastPage);
    }



}
