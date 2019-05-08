package com.telekom.service.api;

import com.telekom.model.dto.Page;

public interface Pagination<T> {
    Page<T> getPage(Integer size, Integer page);
}
