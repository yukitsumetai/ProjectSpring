package com.telekom.service;

import com.telekom.entityDTO.ContractDTO;

public interface ContractService {

    void add(ContractDTO contract);
    ContractDTO getOne(String number);
}
