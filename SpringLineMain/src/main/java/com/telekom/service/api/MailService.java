package com.telekom.service.api;

import com.telekom.model.dto.ContractDto;

import javax.mail.MessagingException;

public interface MailService {

    void sendMessageWithAttachment(Boolean existing, ContractDto contract) throws MessagingException;
}
