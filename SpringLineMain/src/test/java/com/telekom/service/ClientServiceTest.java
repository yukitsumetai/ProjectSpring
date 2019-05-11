package com.telekom.service;


import com.telekom.config.ClientServiceConfig;
import com.telekom.dao.api.ClientDao;
import com.telekom.mapper.ClientMapper;
import com.telekom.model.dto.ClientDto;
import com.telekom.model.dto.Page;
import com.telekom.model.entity.Client;
import com.telekom.service.impl.ClientServiceImpl;
import com.telekom.service.impl.ImageRecognitionImpl;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ClientServiceConfig.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientServiceTest {
    @Autowired
    private ClientServiceImpl clientService;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    ImageRecognitionImpl imageRecognition;
    @Autowired
    Logger logger;


    private static Client client;
    private static ClientDto clientDto;
    private static Client client5;
    private static ClientDto client5Dto;

    private static List<Client> clients;
    private static List<Client> clients5;

    private static List<ClientDto> clientsDto;
    private static List<ClientDto> clients5Dto;
    private Page page;


    @BeforeEach
    void setup() {
        client = new Client();
        client.setId(0);

        client5 = new Client();
        client5.setId(5);

        clientDto = new ClientDto();
        clientDto.setId(0);

        client5Dto = new ClientDto();
        client5Dto.setId(0);

        clients = new ArrayList<>();
        clients.add(client);
        clients5 = new ArrayList<>();
        clients5.add(client5);

        clientsDto = new ArrayList<>();
        clientsDto.add(clientDto);
        clients5Dto = new ArrayList<>();
        clients5Dto.add(client5Dto);

        when(clientMapper.entityToDto(client)).thenReturn(clientDto);
        when(clientMapper.dtoToEntity(clientDto)).thenReturn(client);
        when(clientMapper.entityToDto(client5)).thenReturn(client5Dto);
        when(clientMapper.dtoToEntity(client5Dto)).thenReturn(client5);

        when(clientDao.getOne(0)).thenReturn(client);
        when(clientDao.getOne(5)).thenReturn(client5);
        when(clientDao.getOne(7)).thenReturn(null);
    }

    @Test
    void getPageReturnsClientsPaged() {
        when(clientDao.getPages(1, 1)).thenReturn(clients);
        when(clientDao.getPages(1, 5)).thenReturn(clients5);
        when(clientDao.getPagesCount()).thenReturn((long)6);

        page =clientService.getPage(1, 1);
        assertIterableEquals(clientsDto, page.getData());
        assertEquals(1, page.getCurrentPage());
        assertEquals(6, page.getTotalPages());
        assertEquals(1, page.getLastPage());
       page =clientService.getPage(1, 5);
        assertIterableEquals(clients5Dto, page.getData());
        assertEquals(5, page.getCurrentPage());
        assertEquals(6, page.getTotalPages());
        assertEquals(1, page.getLastPage());

    }

    @Test
    void getPageReturnsEmptyList() {

        when(clientDao.getPages(1, 7)).thenReturn(null);
        when(clientDao.getPagesCount()).thenReturn((long)6);

        page = clientService.getPage(1, 7);
        assertEquals(7, page.getCurrentPage());
        assertTrue(page.getData().isEmpty());
    }


    @Test
    void checkExistingReturnsTrue() {
        when(clientDao.getOneByEmail("test")).thenReturn(true);
        when(clientDao.getOneByPassport(987654321)).thenReturn(true);
        when(clientDao.getOneByPassport(123456789)).thenReturn(false);
        when(clientDao.getOneByEmail("test2")).thenReturn(false);

        assertEquals(true, clientService.checkExisting("test", 987654321));
        assertEquals(true, clientService.checkExisting("test2", 987654321));
        assertEquals(true, clientService.checkExisting("test", 123456789));

    }

    @Test
    void checkExistingReturnsFalse() {
        when(clientDao.getOneByPassport(123456789)).thenReturn(false);
        when(clientDao.getOneByEmail("test2")).thenReturn(false);
        assertEquals(false, clientService.checkExisting("test2", 123456789));
    }

    @Test
    void getClientByUserIdReturnsClient() {
        when(clientDao.getOne((long)0)).thenReturn(client);
        when(clientDao.getOne((long)7)).thenReturn(null);
        assertEquals(clientDto, clientService.getClient((long)0));
        assertNull(clientService.getClient((long) 7));
    }

    @Test
    void getClientByPhoneNumberReturnsClient() {
        BigInteger number = new BigInteger("123456789");
        BigInteger number2 = new BigInteger("987654321");
        when(clientDao.getOne(number)).thenReturn(client);
        when(clientDao.getOne(number2)).thenReturn(null);
        assertEquals(clientDto, clientService.getClient("123456789"));
        assertNull(clientService.getClient("987654321"));
    }

    @Test
    void getClientByIdReturnsClient() {
        when(clientMapper.entityToDtoWithoutContract(client)).thenReturn(clientDto);
        assertEquals(clientDto, clientService.getClient(0));
        assertNull(clientService.getClient(7));
    }

    @Test
    void performOcrPerformsOcr() {
        assertNull(true);
    }

}
