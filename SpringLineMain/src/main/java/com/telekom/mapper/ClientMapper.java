package com.telekom.mapper;

import com.telekom.model.entity.Address;
import com.telekom.model.entity.Client;
import com.telekom.model.entity.Contract;
import com.telekom.model.dto.AddressDto;
import com.telekom.model.dto.ClientDto;
import com.telekom.model.dto.ContractDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientMapper {



    public ClientDto entityToDto(Client t){
        ClientDto tmp= entityToDtoWithoutContract(t);
        List<ContractDto> contracts=new ArrayList<>();
        for (Contract c:t.getContract()
             ) {
           ContractDto c2=new ContractDto();
           c2.setPhoneNumber(c.getPhoneNumber().toString());
           contracts.add(c2);
        }
        tmp.setContracts(contracts);
        return tmp;
    }

    public ClientDto entityToDtoWithoutContract(Client t){
        ClientDto tmp=new ClientDto();
        tmp.setId(t.getId());
        tmp.setName(t.getName());
        tmp.setSurname(t.getSurname());
        tmp.setPassport(t.getPassport());
        tmp.setEmail(t.getEmail());
        tmp.setBirthday(t.getBirthday());
        tmp.setAddress(entityToDtoAddress(t.getAddress()));
        return tmp;
    }



    public AddressDto entityToDtoAddress(Address t){
        AddressDto tmp=new AddressDto();
        tmp.setAddressID(t.getId());
        tmp.setHouseNo(t.getHouseNo());
        tmp.setStreet(t.getStreet());
        tmp.setCity(t.getCity());
        tmp.setZip(t.getZip());
        tmp.setCountry(t.getCountry());
        return tmp;
    }

    public Client dtoToEntity(ClientDto t){
        Client tmp=new Client();
        tmp.setId(t.getId());
        tmp.setName(t.getName());
        tmp.setSurname(t.getSurname());
        tmp.setPassport(t.getPassport());
        tmp.setBirthday(t.getBirthday());
        tmp.setEmail(t.getEmail());
        tmp.setAddress(dtoToEntityAddress(t.getAddress()));
        return tmp;
    }

    public Address dtoToEntityAddress(AddressDto t){
        Address tmp=new Address();
        tmp.setId(t.getAddressID());
        tmp.setHouseNo(t.getHouseNo());
        tmp.setStreet(t.getStreet());
        tmp.setCity(t.getCity());
        tmp.setZip(t.getZip());
        tmp.setCountry(t.getCountry());
        return tmp;
    }
}
