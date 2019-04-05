package com.telekom.mapper;

import com.telekom.entity.Address;
import com.telekom.entity.Client;
import com.telekom.entity.Contract;
import com.telekom.entityDTO.AddressDTO;
import com.telekom.entityDTO.ClientDTO;
import com.telekom.entityDTO.ContractDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientMapper {



    public ClientDTO EntityToDto(Client t){
        ClientDTO tmp=EntityToDtoWithoutContract(t);
        List<ContractDTO> contracts=new ArrayList<>();
        for (Contract c:t.getContract()
             ) {
           ContractDTO c2=new ContractDTO();
           c2.setPhoneNumber(c.getPhoneNumber().toString());
           contracts.add(c2);
        }
        tmp.setContracts(contracts);
        return tmp;
    }

    public ClientDTO EntityToDtoWithoutContract(Client t){
        ClientDTO tmp=new ClientDTO();
        tmp.setId(t.getId());
        tmp.setName(t.getName());
        tmp.setSurname(t.getSurname());
        tmp.setPassport(t.getPassport());
        tmp.setEmail(t.getEmail());
        tmp.setBirthday(t.getBirthday());
        tmp.setAddress(EntityToDtoAddress(t.getAddress()));
        return tmp;
    }



    public AddressDTO EntityToDtoAddress(Address t){
        AddressDTO tmp=new AddressDTO();
        tmp.setAddressID(t.getId());
        tmp.setHouseNo(t.getHouseNo());
        tmp.setStreet(t.getStreet());
        tmp.setCity(t.getCity());
        tmp.setZip(t.getZip());
        tmp.setCountry(t.getCountry());
        return tmp;
    }

    public Client DtoToEntity(ClientDTO t){
        Client tmp=new Client();
        tmp.setId(t.getId());
        tmp.setName(t.getName());
        tmp.setSurname(t.getSurname());
        tmp.setPassport(t.getPassport());
        tmp.setBirthday(t.getBirthday());
        tmp.setEmail(t.getEmail());
        tmp.setAddress(DtoToEntityAddress(t.getAddress()));
        return tmp;
    }

    public Address DtoToEntityAddress(AddressDTO t){
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
