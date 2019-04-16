package com.telekom.service.api;
import com.telekom.model.dto.ClientDto;
import com.telekom.model.dto.Page;

public interface ClientService {

    void add(ClientDto client);
    Boolean checkExisting(String email, Integer passport);
    ClientDto getOne(String number);//get by phone number
    ClientDto getOne(Integer number);//get by ClientId
    ClientDto getOne(Long id);
    Page<ClientDto> getPage(Integer size, Integer page);
}

