package com.telekom.service;

import com.telekom.config.ContractServiceConfig;
import com.telekom.dao.api.*;
import com.telekom.mapper.ClientMapper;
import com.telekom.mapper.ContractMapper;
import com.telekom.model.dto.*;
import com.telekom.model.entity.*;
import com.telekom.service.api.*;
import com.telekom.service.impl.ContractServiceImpl;
import com.telekom.utils.api.MailService;
import com.telekom.utils.api.PdfCreator;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.mail.MessagingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.telekom.model.entity.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ContractServiceConfig.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContractServiceTest {
    @Autowired
    private ContractServiceImpl contractService;
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private OptionService optionService;
    @Autowired
    private OptionGroupService optionGroupService;
    @Autowired
    private TariffService tariffService;
    @Autowired
    private OptionDao optionDao;
    @Autowired
    private TariffDao tariffDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private PhoneNumberDao phoneNumberDao;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    PdfCreator pdfCreator;
    @Autowired
    MailService mailSender;

    @Autowired
    Logger logger;


    private static Contract contract;
    private static ContractDto contractDto;

    private static Tariff tariff;
    private static TariffDto tariffDto;

    private static Option option;
    private static OptionDto optionDto;
    private static Option option5;
    private static OptionDto option5Dto;

    private static List<Option> options;
    private static List<Option> options5;

    private static List<OptionDto> optionsDto;
    private static List<OptionDto> options5Dto;
    private static OptionGroup og;
    private static OptionGroupDto ogDto;
    private static List<OptionGroup> ogs;
    private static List<OptionGroupDto> ogsDto;


    @BeforeEach
    void setup() {

        tariff = new Tariff();
        tariff.setId(0);
        tariff.setPrice(3.33);
        tariffDto = new TariffDto();
        tariffDto.setId(0);
        tariffDto.setPrice(3.33);

        option = new Option();
        option.setId(0);
        option.setValid(true);
        option.setPriceMonthly(2);
        option.setPriceOneTime(5);
        option.setCompatibleTariffs(new HashSet<>());


        option5 = new Option();
        option5.setId(5);
        option5.setValid(false);
        option5.setParent(option);
        option5.setCompatibleTariffs(new HashSet<>());


        optionDto = new OptionDto();
        optionDto.setId(0);
        optionDto.setPriceMonthly(2);
        optionDto.setPriceOneTime(5);
        optionDto.setIsValid(true);
        optionDto.setCompatibleTariffs(new HashSet<>());


        option5Dto = new OptionDto();
        option5Dto.setId(0);
        option5Dto.setIsValid(false);
        option5Dto.setParent(optionDto);
        option5Dto.setCompatibleTariffs(new HashSet<>());

        options = new ArrayList<>();
        options.add(option);
        options5 = new ArrayList<>();
        options5.add(option5);

        optionsDto = new ArrayList<>();
        optionsDto.add(optionDto);
        options5Dto = new ArrayList<>();
        options5Dto.add(option5Dto);

        og = new OptionGroup();
        og.setId(1);
        og.setValid(true);
        ogDto = new OptionGroupDto();
        ogDto.setId(1);
        ogDto.setIsValid(true);
        ogs = new ArrayList<>();
        ogs.add(og);
        ogsDto = new ArrayList<>();
        ogsDto.add(ogDto);

        contract = new Contract();
        contractDto = new ContractDto();
        contractDto.setPhoneNumber("0");
        contract.setPhoneNumber(new BigInteger("0"));
        contractDto.setTariff(tariffDto);
        contract.setTariff(tariff);
        contract.setOptions(new HashSet<>());
        contractDto.setOptions(new HashSet<>());

        //when(optionGroupDao.getOne(1)).thenReturn(og);

        when(contractMapper.entityToDto(contract)).thenReturn(contractDto);
        when(contractMapper.dtoToEntity(contractDto)).thenReturn(contract);


        when(tariffService.getTariff(0)).thenReturn(tariffDto);
        when(optionService.getOne(0)).thenReturn(optionDto);
        when(optionService.getOne(5)).thenReturn(option5Dto);
        when(optionService.getOne(7)).thenReturn(null);
        when(contractDao.getOne(new BigInteger("0"))).thenReturn(contract);
        when(contractDao.getOne(new BigInteger("7"))).thenReturn(null);

    }

    @Test
    void setTariffSetsTariff() {
        tariffDto.setIsValid(true);


        assertTrue(contractService.setTariff(contractDto, 0));
        assertEquals(tariffDto, contractDto.getTariff());
        assertEquals(3.33, contractDto.getPrice());
    }

    @Test
    void setTariffReturnsFalse() {
        tariffDto.setIsValid(false);
        tariffDto.setPrice(3.33);
        assertFalse(contractService.setTariff(contractDto, 0));
    }

    @Test
    void getGroupsReturnsGtoups() {
        contractDto.setTariff(tariffDto);
        Set ogSet = new HashSet();
        ogSet.add(ogDto);
        when(optionGroupService.findByTariff(0)).thenReturn(ogSet);
        assertEquals(ogSet, contractService.getGroups(contractDto));
    }

    @Test
    void getOptionParentsReturnsOptionParents() {
        contractDto.setTariff(tariffDto);
        Set oSet = new HashSet();
        oSet.add(optionDto);
        when(optionService.findByTariff(0)).thenReturn(oSet);
        assertEquals(oSet, contractService.getOptionsParents(contractDto));
    }

    @Test
    void getOptionChildrenReturnsOptionChildren() {
        contractDto.setTariff(tariffDto);
        Set oSet = new HashSet();
        oSet.add(option5Dto);
        when(optionService.findByTariffChildren(0)).thenReturn(oSet);
        assertEquals(oSet, contractService.getOptionsChildren(contractDto));
    }

    @Test
    void setOptionsSetsOption() {
        contractDto.setTariff(tariffDto);
        List<Integer> id = new ArrayList<>();
        id.add(0);

        assertTrue(contractService.setOptions(contractDto, id, true));
        assertEquals(5, contractDto.getPriceOneTime());
        assertEquals(5.33, contractDto.getPrice());
        assertIterableEquals(optionsDto, contractDto.getOptions());
    }

    @Test
    void setOptionsSetsOptionExisting() {
        contractDto.setTariff(tariffDto);
        List<Integer> id = new ArrayList<>();
        id.add(0);
        contractDto.addOption(optionDto);

        assertTrue(contractService.setOptions(contractDto, id, true));
        assertEquals(0, contractDto.getPriceOneTime());
        assertEquals(5.33, contractDto.getPrice());
        assertIterableEquals(optionsDto, contractDto.getOptions());
    }


    @Test
    void setOptionsSetsNothing() {
        contractDto.setTariff(tariffDto);
        List<Integer> id = new ArrayList<>();
        id.add(7);

        assertTrue(contractService.setOptions(contractDto, id, true));
        assertEquals(0, contractDto.getPriceOneTime());
        assertEquals(3.33, contractDto.getPrice());
        assertTrue(contractDto.getOptions().isEmpty());
    }

    @Test
    void existingOptionsRemovesOneTimePriceForExisting() {
        contractDto.addOption(optionDto);
        contractService.existingOptionsRemoveOneTimePrice(contractDto, optionDto);
        assertEquals(0, optionDto.getPriceOneTime());
    }

    @Test
    void existingOptionsNotRemovesOneTimePriceForNew() {
        contractService.existingOptionsRemoveOneTimePrice(contractDto, optionDto);
        assertEquals(5, optionDto.getPriceOneTime());
    }

    @Test
    void getTariffsForAddReturnsTariffs() {
        TariffDto tariff2 = new TariffDto();
        tariff2.setId(2);
        contractDto.setTariff(tariff2);

        List tariffs = new ArrayList();
        tariffs.add(tariffDto);
        tariffs.add(tariff2);
        when(tariffService.getAllValid()).thenReturn(tariffs);
        List result = new ArrayList();
        result.add(tariffDto);

        assertIterableEquals(result, contractService.getTariffsForAdd(contractDto));
    }

    @Test
    void getOneReturnsContract() {
        assertEquals(contractDto, contractService.getOne("0"));
        assertNull(contractService.getOne("7"));
    }

    @Test
    void blockBlocksByUser() {
        contractService.block(contractDto, false);
        assertTrue(contract.isBlocked());
        assertFalse(contract.isAgentBlock());
    }

    @Test
    void blockBlocksByAdmin() {
        contractService.block(contractDto, true);
        assertTrue(contract.isBlocked());
        assertTrue(contract.isAgentBlock());
    }

    @Test
    void unblockUnblocksByUser() {
        contractDto.setBlocked(true);
        contractDto.setAgentBlock(false);
        contract.setBlocked(true);
        contract.setAgentBlock(false);
        contractService.unblock(contractDto, false);
        assertFalse(contract.isBlocked());
        assertFalse(contract.isAgentBlock());
    }

    @Test
    void unblockNoUnblocksByUser() {
        contractDto.setBlocked(true);
        contractDto.setAgentBlock(true);
        contract.setBlocked(true);
        contract.setAgentBlock(true);
        contractService.unblock(contractDto, false);
        assertTrue(contract.isBlocked());
        assertTrue(contract.isAgentBlock());
    }

    @Test
    void unblockUnblocksByAdmin() {
        contractDto.setBlocked(true);
        contractDto.setAgentBlock(false);
        contract.setBlocked(true);
        contract.setAgentBlock(false);
        contractService.unblock(contractDto, true);
        assertFalse(contract.isBlocked());
        assertFalse(contract.isAgentBlock());
    }

    @Test
    void unblockUnblocksByAdminLockedByAdmin() {
        contractDto.setBlocked(true);
        contractDto.setAgentBlock(true);
        contract.setBlocked(true);
        contract.setAgentBlock(true);
        contractService.unblock(contractDto, true);
        assertFalse(contract.isBlocked());
        assertFalse(contract.isAgentBlock());
    }

    @Test
    void getParentsForExistingContractReturnsParentsExistingInList() {
        contractDto.addOption(optionDto);
        contractDto.addOption(option5Dto);
        option5Dto.setParent(optionDto);
        Set oSet = new HashSet();
        oSet.add(optionDto);
        when(contractService.getOptionsParents(contractDto)).thenReturn(oSet);

        Set<OptionDto> tmp = contractService.getParentsForExistingContract(contractDto);
        for (OptionDto o : tmp
        ) {
            assertTrue(o.isExisting());
            assertEquals(0, o.getPriceOneTime());
        }
    }

    @Test
    void getParentsForExistingContractReturnsParentsExistingNotInList() {
        contractDto.addOption(option5Dto);
        option5Dto.setPriceOneTime(7);
        Set oSet = new HashSet();
        oSet.add(optionDto);
      when(contractService.getOptionsParents(contractDto)).thenReturn(null);

        Set<OptionDto> tmp = contractService.getParentsForExistingContract(contractDto);
        for (OptionDto o : tmp
        ) {
            assertTrue(o.isExisting());
            assertEquals(0, o.getPriceOneTime());
        }
    }

    @Test
    void getParentsForExistingContractReturnsParents() {
        Set oSet = new HashSet();
        oSet.add(optionDto);
        when(optionService.findByTariff(0)).thenReturn(oSet);

        Set<OptionDto> tmp = contractService.getParentsForExistingContract(contractDto);
        for (OptionDto o : tmp
        ) {
            assertFalse(o.isExisting());
            assertEquals(5, o.getPriceOneTime());
        }
    }

    @Test
    void geChildrenForExistingContractReturnsChildrenExistingInList() {
        contractDto.addOption(optionDto);
        contractDto.addOption(option5Dto);
        option5Dto.setParent(optionDto);
        Set oSet = new HashSet();
        oSet.add(option5Dto);
        when(optionService.findByTariffChildren(0)).thenReturn(oSet);

        Set<OptionDto> tmp = contractService.getChildrenForExistingContract(contractDto);
        for (OptionDto o : tmp
        ) {
            assertTrue(o.isExisting());
            assertEquals(0, o.getPriceOneTime());
        }
    }

    @Test
    void getChildrenForExistingContractReturnsChildrenExistingNotInList() {
        contractDto.addOption(option5Dto);
        option5Dto.setParent(optionDto);
        option5Dto.setPriceOneTime(7);
        when(optionService.findByTariffChildren(0)).thenReturn(null);

        Set<OptionDto> tmp = contractService.getParentsForExistingContract(contractDto);
        for (OptionDto o : tmp
        ) {
            assertTrue(o.isExisting());
            assertEquals(0, o.getPriceOneTime());
        }
    }

    @Test
    void getChildrenReturnsOptionChildren() {
        contractDto.setTariff(tariffDto);
        tariffDto.setId(0);

        Set oSet = new HashSet();
        oSet.add(optionDto);
        when(optionService.findByTariffChildren(0)).thenReturn(oSet);

        Set<OptionDto> tmp = contractService.getChildrenForExistingContract(contractDto);
        for (OptionDto o : tmp
        ) {
            assertFalse(o.isExisting());
            assertEquals(5, o.getPriceOneTime());
        }
    }

    @Test
    void addContractAddsContractExitingClient() {
        contract.setPhoneNumber(new BigInteger("0"));
        ClientDto clientDto = new ClientDto();
        clientDto.setId(0);
        clientDto.setPassword("test");
        Client client = new Client();
        User user = new User();
        user.setId((long) 0);
        client.setUser(user);
        when(clientMapper.dtoToEntity(clientDto)).thenReturn(client);
        contractDto.setClient(clientDto);
        when(clientDao.getOne(0)).thenReturn(client);
        when(userDao.getOne((long) 0)).thenReturn(user);
        List contracts = new ArrayList();
        contracts.add(contract);

        contractService.add(contractDto);
        verify(phoneNumberDao, atLeastOnce()).deleteNumber(new BigInteger("0"));
        verify(contractDao, atLeastOnce()).add(contract);
        assertEquals(client, contract.getClient());
        assertIterableEquals(contracts, user.getContract());
    }

    @Test
    void addContractAddsContractNewClient() {
        contract.setPhoneNumber(new BigInteger("0"));
        ClientDto clientDto = new ClientDto();
        clientDto.setPassword("test");
        Client client = new Client();
        contractDto.setClient(clientDto);
        when(clientDao.getOne(0)).thenReturn(null);
        when(clientMapper.dtoToEntity(clientDto)).thenReturn(client);
        List contracts = new ArrayList();
        contracts.add(contract);

        contractService.add(contractDto);
        when(passwordEncoder.encode(contractDto.getClient().getPassword())).thenReturn("test2");
        verify(phoneNumberDao).deleteNumber(new BigInteger("0"));
        verify(clientDao).add(client);
        verify(contractDao).add(contract);
        assertEquals(client, contract.getClient());
        assertIterableEquals(contracts, client.getUser().getContract());
        assertEquals(ROLE_USER, client.getUser().getRole());
    }

    @Test
    void updateUpdatesTariff() {
        Tariff t2 = new Tariff();
        t2.setId(2);
        contract.setTariff(t2);
        contract.setPrice(2.0);
        Set ops= new HashSet();
        ops.add(option);
        contract.setOptions(ops);
        contractDto.setTariff(tariffDto);
        contractDto.setPrice(0.0);
        when(tariffDao.getOne(0)).thenReturn(tariff);

        contractService.update(contractDto);
        assertEquals(tariff, contract.getTariff());
        assertEquals(0.0, contract.getPrice());
        assertTrue(contract.getOptions().isEmpty());
    }



    @Test
    void updateUpdatesOptionsEmpty() {
        contract.setTariff(tariff);
        contract.setPrice(2.0);
        contractDto.setTariff(tariffDto);
        contractDto.setPrice(0.0);

        contractService.update(contractDto);
        assertEquals(tariff, contract.getTariff());
        assertTrue(contract.getOptions().isEmpty());
        assertEquals(0.0, contract.getPrice());
    }

    @Test
    void updateUpdatesOptions() {
        contract.setTariff(tariff);
        contract.setPrice(2.0);
        contractDto.setTariff(tariffDto);
        Set ops= new HashSet();
        ops.add(optionDto);
        contractDto.setOptions(ops);
        contractDto.setPrice(3.0);
         when(optionDao.getOne(0)).thenReturn(option);
        Set ops2= new HashSet();
        ops2.add(option);

        contractService.update(contractDto);
        assertEquals(tariff, contract.getTariff());
        assertIterableEquals(ops2, contract.getOptions());
        assertEquals(3.0, contract.getPrice());
    }

    @Test
    void pdfReturnsTrue()  {
        when( mailSender.sendMessageWithAttachment(true, contractDto)).thenReturn(true);
        assertTrue(contractService.sendEmail(true, contractDto));
        verify(mailSender).sendMessageWithAttachment(true, contractDto);

    }

    @Test
    void pdfReturnsFalse() throws MessagingException {
        when( mailSender.sendMessageWithAttachment(false, contractDto)).thenReturn(true);
        assertFalse(contractService.sendEmail(true, contractDto));
        verify(mailSender).sendMessageWithAttachment(true, contractDto);
    }
}
