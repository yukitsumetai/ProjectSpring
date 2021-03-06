package com.telekom.service.api;

import com.telekom.model.dto.*;

import java.util.List;
import java.util.Set;

public interface ContractService {

    void add(ContractDto contract);

    ContractDto getOne(String number);

    boolean setOptions(ContractDto contract, List<Integer> id, boolean existing);

    boolean setTariff(ContractDto contract, Integer id);

    List<TariffDto> getTariffsForAdd(ContractDto contract);

    void update(ContractDto contract);

    Set<OptionGroupDto> getGroups(ContractDto contract);

    Set<OptionDto> getOptionsParents(ContractDto contract);

    Set<OptionDto> getOptionsChildren(ContractDto contract);

    Set<OptionDto> getParentsForExistingContract(ContractDto contract);

    Set<OptionDto> getChildrenForExistingContract(ContractDto contract);

    void block(ContractDto contract, boolean admin);

    void unblock(ContractDto contract, boolean admin);

    boolean sendEmail(Boolean newClient, ContractDto contract);

}
