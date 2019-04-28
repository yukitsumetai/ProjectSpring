package com.telekom.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.telekom.dao.api.*;
import com.telekom.model.dto.ContractDto;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.OptionGroupDto;
import com.telekom.model.dto.TariffDto;
import com.telekom.mapper.ClientMapper;
import com.telekom.mapper.ContractMapper;
import com.telekom.model.entity.*;
import com.telekom.service.api.ContractService;
import com.telekom.service.api.OptionGroupService;
import com.telekom.service.api.OptionService;
import com.telekom.service.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private PhoneNumberDao phoneNumberDao;
    @Autowired
    private ClientDAO clientDao;
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

    @Override
    @Transactional
    public void add(ContractDto contract) {

        Contract tmp = contractMapper.dtoToEntity(contract);
        phoneNumberDao.deleteNumber(tmp.getPhoneNumber());
        Client c = clientDao.getOne(contract.getClient().getId());
        if (c == null) {
            c = clientMapper.dtoToEntity(contract.getClient());
            c.setContract(tmp);
            User user = new User(tmp, passwordEncoder.encode(contract.getClient().getPassword()));
            c.setUser(user);
            clientDao.add(c);
        } else {
            User user = userDao.getOne(c.getUser().getId());
            user.addContract(tmp);
        }
        contractDao.add(tmp);
        tmp.setClient(c);
    }

    @Override
    public Set<OptionDto> getParentsForExisting(ContractDto contract) {
        Set<OptionDto> options = getOptions(contract);
        Set<OptionDto> existing = contract.getOptions();
        for (OptionDto o : existing) {
            if (o.getParent() == null) {
                setExisting(options, o);
            }
        }
        return options;
    }


    @Override
    public Set<OptionDto> getChildrenForExisting(ContractDto contract) {
        Set<OptionDto> existing = contract.getOptions();
        Set<OptionDto> children = getOptionsChildren(contract);
        for (OptionDto o : existing) {
            if (o.getParent() != null) {
                setExisting(children, o);
            }
        }
        return children;
    }


    private void setExisting(Set<OptionDto> target, OptionDto existing) {
        boolean flag = true;
        for (OptionDto c : target) {
            if (c.getId() == existing.getId()) {
                flag = false;
                c.setExisting(true);
                c.setPriceOneTime(0.00);
            }
        }
        if (flag) {
            existing.setExisting(true);
            existing.setPriceOneTime(0.00);
            target.add(existing);
        }

    }

    @Override
    public List<TariffDto> getTariffsForAdd(ContractDto contract) {
        Integer id = contract.getTariff().getId();
        List<TariffDto> tariffs = new ArrayList<>(tariffService.getAllValid());
        tariffs.removeIf(st -> st.getId() == id);
        return tariffs;
    }


    @Override
    public boolean setOptions(ContractDto contract, List<Integer> id, boolean existing) {
        Set<OptionDto> temporary = new HashSet<>();

        if (id != null) {
            contract.setPriceOneTime(0.00);
            contract.setPrice(0.00);
            contract.addPrice(contract.getTariff().getPrice());
            for (Integer i : id) {
                OptionDto tmp = optionService.getOne(i);
                if (tmp != null) { //0 exist
                    temporary.add(tmp);
                    contract.addPrice(tmp.getPriceMonthly());
                    if (contract.getOptions() != null) {
                        for (OptionDto op : contract.getOptions()
                        ) {
                            if (op.getId() == tmp.getId()) tmp.setPriceOneTime(0.00); //if option already existed
                        }
                    }
                    contract.addPriceOneTime(tmp.getPriceOneTime());
                }
            }
            contract.setOptions(temporary);
            if (!existing) return optionValidation(contract);
        } else {
            contract.setOptions(new HashSet<>());
            contract.setPrice(0.00);
            contract.addPrice(contract.getTariff().getPrice());
        }
        return true;
    }

    @Override
    public Set<OptionDto> getOptions(ContractDto contract) {
        return optionService.findByTariff(contract.getTariff().getId());
    }

    private boolean optionValidation(ContractDto contract) {
        if (basicOptionValidation(contract)) {
            if (childrenOptionValidation(contract)) return incompatibleOptionValidation(contract);
        }
        return false;
    }

    private boolean basicOptionValidation(ContractDto contract) {
        for (OptionDto o : contract.getOptions()) {
            //if (o.isIsValid() == false) return false;

            boolean flag = false;

            for (OptionDto o2 : contract.getTariff().getOptions()) {
                if (o2.getId() == o.getId()) flag = true;
            }
            if (!flag) return false;
        }
        return true;
    }


    private boolean childrenOptionValidation(ContractDto contract) {
        for (OptionDto o : contract.getOptions()) {
            if (o.getParent() != null) {
                boolean flag = false;
                for (OptionDto o2 : contract.getOptions()) {
                    if (o.getParent().getId() == o2.getId()) flag = true;
                }
                if (!flag) return false;
            }
        }
        return true;
    }

    private boolean incompatibleOptionValidation(ContractDto contract) {
        if (!contract.getOptions().isEmpty()) {
            Set<OptionGroupDto> og = new HashSet<>();
            for (OptionDto o : contract.getOptions()
            ) {
                if (o.getOptionGroup() != null) {
                    og.add(o.getOptionGroup());
                }
            }
            for (OptionGroupDto og2 : og
            ) {
                int count = 0;
                for (OptionDto o : contract.getOptions()) {
                    if (o.getOptionGroup() != null) {
                        if (o.getOptionGroup().getId() == og2.getId()) count++;
                    }
                }
                if (count >= 2) return false;
            }
        }
        return true;
    }

    @Override
    public Set<OptionDto> getOptionsChildren(ContractDto contract) {
        return optionService.findByTariffChildren(contract.getTariff().getId());
    }

    @Override
    public boolean setTariff(ContractDto contract, Integer id) {
        TariffDto tmp = tariffService.getOne(id);
        if (!tmp.isIsValid()) return false;
        contract.setTariff(tmp);
        contract.setPrice(0.0);
        contract.addPrice(tmp.getPrice());
        return true;
    }

    @Override
    public Set<OptionGroupDto> getGroups(ContractDto contract) {
        return optionGroupService.findByTariff(contract.getTariff().getId());
    }

    @Override
    @Transactional
    public void update(ContractDto contractDto) {
        Contract contract = contractDao.getOne(contractDto.getPhoneNumberInt());
        if (contract.getTariff().getId() != contractDto.getTariff().getId()) {
            contract.setOptions(null);
            Tariff t = tariffDao.getOne(contractDto.getTariff().getId());
            contract.setTariff(t);
        } else {
            contract.setOptions(new HashSet<>());
            if (!contractDto.getOptions().isEmpty()) {
                for (OptionDto o : contractDto.getOptions()
                ) {
                    Option tmp2 = optionDao.getOne(o.getId());
                    contract.addOption(tmp2);
                }
            }
        }
        contract.setPrice(0.0);
        contract.setPrice(contractDto.getPrice());
    }

    @Override
    @Transactional
    public ContractDto getOne(String number) {
        BigInteger number2 = new BigInteger(number);
        ContractDto tmp;
        try {
            tmp = contractMapper.entityToDto(contractDao.getOne(number2));
        } catch (NullPointerException e) {
            tmp = null;
        }
        return tmp;
    }
    private static final String DEST = "test.pdf";

    @Override
    @Transactional
    public void block(ContractDto contract, boolean admin) {
        Contract tmp = contractDao.getOne(contract.getPhoneNumberInt());
        tmp.setBlocked(true);
        if (admin) {
            tmp.setAgentBlock(true);
        }
        createPdf();

    }

    @Override
    @Transactional
    public void unblock(ContractDto contract, boolean admin) {
        Contract tmp = contractDao.getOne(contract.getPhoneNumberInt());
        if (tmp.isAgentBlock()) {
            if (admin) {
                tmp.setAgentBlock(false);
                tmp.setBlocked(false);
            }
        } else {
            tmp.setAgentBlock(false);
            tmp.setBlocked(false);
        }
    }


    private void createPdf() {
        File file = new File(DEST);

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(DEST));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Hello World", font);

            document.add(chunk);
            document.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (DocumentException e) {
            System.out.println("Document exception");
        }
        System.out.println("Dhgj");
    }

}



