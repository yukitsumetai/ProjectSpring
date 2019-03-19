package com.telekom.mapper;

import com.telekom.entity.Address;
import com.telekom.entity.Client;
import com.telekom.entityDTO.AddressDTO;
import com.telekom.entityDTO.ClientDTO;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public ClientDTO EntityToDto(Client t){
        ClientDTO tmp=new ClientDTO();
        tmp.setId(t.getId());
        tmp.setName(t.getName());
        tmp.setSurname(t.getSurname());
        tmp.setPassport(t.getPassport());
        tmp.setEmail(t.getEmail());
        tmp.setBirthday(t.getBirthday());
        tmp.setPassword(t.getPassword());
        tmp.setAddress(EntityToDtoAddress(t.getAddress()));
        return tmp;
    }



    public AddressDTO EntityToDtoAddress(Address t){
        AddressDTO tmp=new AddressDTO();
        tmp.setAddressID(t.getAddressID());
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
        tmp.setPassword(t.getPassword());
        tmp.setEmail(t.getEmail());
        tmp.setAddress(DtoToEntityAddress(t.getAddress()));
        return tmp;
    }

    public Address DtoToEntityAddress(AddressDTO t){
        Address tmp=new Address();
        tmp.setAddressID(t.getAddressID());
        tmp.setHouseNo(t.getHouseNo());
        tmp.setStreet(t.getStreet());
        tmp.setCity(t.getCity());
        tmp.setZip(t.getZip());
        tmp.setCountry(t.getCountry());
        return tmp;
    }
}
