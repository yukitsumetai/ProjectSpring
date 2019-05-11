package com.telekom.service;


import com.telekom.config.PhoneNumberServiceConfig;
import com.telekom.dao.api.PhoneNumberDao;
import com.telekom.model.dto.Page;
import com.telekom.model.entity.PhoneNumber;
import com.telekom.service.impl.PhoneNumberServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PhoneNumberServiceConfig.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PhoneNumberServiceTest {

    @Autowired
    private PhoneNumberServiceImpl phoneNumberService;

    @Autowired
    private PhoneNumberDao phoneNumberDao;


    private Page page;

    private static PhoneNumber phoneNumber;

    @Test
    void getPageReturnsPhoneNumbersPaged() {
        List<PhoneNumber> numbers=new ArrayList<>();
        phoneNumber = new PhoneNumber();
        numbers.add(phoneNumber);

        when(phoneNumberDao.getPages(1, 1)).thenReturn(numbers);
        when(phoneNumberDao.getPagesCount()).thenReturn((long) 6);

        page = phoneNumberService.getPage(1, 1);
        assertEquals(numbers, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(6, page.getTotalPages());
        assertEquals(1, page.getLastPage());

    }

    @Test
    void getPageReturnsEmptyWhenNotFound() {
        when(phoneNumberDao.getPages(1, 7)).thenReturn(null);
        page = phoneNumberService.getPage(1, 7);
        assertNull(page.getData());
        assertEquals(7, page.getCurrentPage());
    }

}
