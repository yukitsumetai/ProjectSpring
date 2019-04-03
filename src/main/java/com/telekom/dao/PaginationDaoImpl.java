package com.telekom.dao;

import javax.persistence.Query;
import java.io.Serializable;

public class PaginationDaoImpl< T extends Serializable > {

    void pageCount(Integer page, Integer size, Query q){
        int pageNumber = page;
        int pageSize = size;
        q.setFirstResult((pageNumber-1) * pageSize);
        q.setMaxResults(pageSize);
    }


}
