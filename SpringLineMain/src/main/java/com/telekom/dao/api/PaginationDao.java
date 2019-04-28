package com.telekom.dao.api;

import java.util.List;

public interface PaginationDao<T>{
    List<T> getPages(Integer size, Integer page);
    Long getPagesCount();
}
