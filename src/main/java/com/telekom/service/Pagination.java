package com.telekom.service;

import com.telekom.entityDTO.Page;

public interface Pagination<T> {
    Page<T> getPage(Integer size, Integer page);
}
