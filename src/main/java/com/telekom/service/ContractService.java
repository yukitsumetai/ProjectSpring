package com.telekom.service;

import com.telekom.entityDTO.ContractDTO;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.TariffDTO;

import java.util.List;
import java.util.Set;

public interface ContractService {

    void add(ContractDTO contract);

    ContractDTO getOne(String number);

    ContractDTO deleteOption(ContractDTO contract, Integer id);

    void setOptions(ContractDTO contract, List<Integer> id);

    List<OptionDTO> setTariff(ContractDTO contract, Integer id);

    void setOptionsAndUpdate(ContractDTO contract, List<Integer> id);

    Set<OptionDTO> getOptionsForAdd(ContractDTO contract);

    List<TariffDTO> getTariffsForAdd(ContractDTO contract);
    void setTariffAndUpdate(ContractDTO contract, Integer id);
}
