package com.telekom.service.api;

import com.telekom.model.dto.ContractDto;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;

public interface MailService {

    void sendMessageWithAttachment(Boolean existing, ContractDto contract) throws MessagingException;
}
