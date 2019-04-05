package com.telekom.service;
import com.telekom.entityDTO.ClientDTO;
import com.telekom.entityDTO.Page;

public interface ClientService {

    void add(ClientDTO client);
    Boolean checkExisting(String email, Integer passport);
    ClientDTO getOne(String number);//get by phone number
    ClientDTO getOne(Integer number);//get by ClientId
    ClientDTO getOne(Long id);
    Page<ClientDTO> getPage(Integer size, Integer page);
}

