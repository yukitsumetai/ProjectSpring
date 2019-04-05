package com.telekom.service;


import com.telekom.dao.PhoneNumberDao;
import com.telekom.entity.PhoneNumber;
import com.telekom.entityDTO.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service

public class PhoneNumberServiceImpl extends PaginationImpl<PhoneNumber> implements PhoneNumberService {


    @Autowired
    private PhoneNumberDao phoneNumberDao;

    @Override
    public List<String> getAll() {
        List<String> numbers=new ArrayList<>();
        for (PhoneNumber n:
        phoneNumberDao.getAll()) {
            numbers.add(n.getNumber().toString());
        }
        return numbers;
    }
    @Override
    @Transactional
    public Page<PhoneNumber> getPage(Integer size, Integer page) {
        List<PhoneNumber> pageGroups =phoneNumberDao.getPages(size, page);
        Long total=phoneNumberDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

}