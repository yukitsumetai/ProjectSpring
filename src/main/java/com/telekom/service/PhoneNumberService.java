package com.telekom.service;

import java.util.List;

public interface PhoneNumberService extends Pagination {
    List<String> getAll();
}

