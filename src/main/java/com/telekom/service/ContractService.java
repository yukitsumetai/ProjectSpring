package com.telekom.service;

import com.telekom.entityDTO.ContractDTO;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.OptionGroupDTO;
import com.telekom.entityDTO.TariffDTO;

import java.util.List;
import java.util.Set;

public interface ContractService {

    void add(ContractDTO contract);

    ContractDTO getOne(String number);

    boolean setOptions(ContractDTO contract, List<Integer> id, boolean existing);

    boolean setTariff(ContractDTO contract, Integer id);

    List<TariffDTO> getTariffsForAdd(ContractDTO contract);

    void update(ContractDTO contract);

    Set<OptionGroupDTO> getGroups(ContractDTO contract);

    Set<OptionDTO> getOptions(ContractDTO contract);

    Set<OptionDTO> getOptionsChildren(ContractDTO contract);

    Set<OptionDTO> getParentsForExisting(ContractDTO contract);

    Set<OptionDTO> getChildrenForExisting(ContractDTO contract);

    void block(ContractDTO contract, boolean admin);

    void unblock(ContractDTO contract, boolean admin);

}
