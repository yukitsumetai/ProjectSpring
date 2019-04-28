package com.telekom.service.impl;


import com.telekom.dao.api.PhoneNumberDao;
import com.telekom.model.entity.PhoneNumber;
import com.telekom.model.dto.Page;
import com.telekom.service.api.PhoneNumberService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service

public class PhoneNumberServiceImpl extends PaginationImpl<PhoneNumber> implements PhoneNumberService {


    @Autowired
    private PhoneNumberDao phoneNumberDao;

    @Autowired
    private Logger logger;

    @Override
    @Transactional
    public Page<PhoneNumber> getOptions(Integer size, Integer page) {
        logger.info("Getting phone number, page "+page);
        List<PhoneNumber> pageGroups =phoneNumberDao.getPages(size, page);
        Long total=phoneNumberDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

}