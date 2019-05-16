package com.telekom.service.api;
import com.telekom.model.dto.ClientDto;
import com.telekom.model.dto.Page;

public interface ClientService extends Pagination {

    void add(ClientDto client);
    Boolean checkExisting(String email, Integer passport);
    ClientDto getClient(String number);//get by phone number
    ClientDto getClient(Integer number);//get by ClientId
    ClientDto getClient(Long id);
    Page<ClientDto> getPage(Integer size, Integer page);
    ClientDto performOcr(String image, String id);
}

