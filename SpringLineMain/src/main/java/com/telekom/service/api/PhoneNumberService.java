package com.telekom.service.api;

import java.util.List;

public interface PhoneNumberService extends Pagination {
    List<String> getAll();
}

