package com.telekom.service.impl;


import com.telekom.dao.api.PhoneNumberDao;
import com.telekom.model.entity.FreePhoneNumber;
import com.telekom.model.dto.Page;
import com.telekom.service.api.PhoneNumberService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service

public class PhoneNumberServiceImpl extends PaginationImpl<FreePhoneNumber> implements PhoneNumberService {


    @Autowired
    private PhoneNumberDao phoneNumberDao;

    @Autowired
    private Logger logger;

    /**
     * Returns page of phone numbers
     * @param size of the page
     * @param page number of the page
     * @return group of phone numbers
     */
    @Override
    @Transactional
    public Page<FreePhoneNumber> getPage(Integer size, Integer page) {
        logger.info("Getting phone number, page "+page);
        List<FreePhoneNumber> pageGroups =phoneNumberDao.getPages(size, page);
        Long total=phoneNumberDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

}