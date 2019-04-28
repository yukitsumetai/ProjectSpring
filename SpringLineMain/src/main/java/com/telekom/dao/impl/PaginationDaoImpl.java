package com.telekom.dao.impl;

import javax.persistence.Query;

public class PaginationDaoImpl {

    void pageCount(Integer page, Integer size, Query q){
        int pageNumber = page;
        int pageSize = size;
        q.setFirstResult((pageNumber-1) * pageSize);
        q.setMaxResults(pageSize);
    }


}
