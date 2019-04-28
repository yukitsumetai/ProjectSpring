package com.telekom.service.api;

import com.telekom.model.dto.Page;

public interface Pagination<T> {
    Page<T> getOptions(Integer size, Integer page);
}
