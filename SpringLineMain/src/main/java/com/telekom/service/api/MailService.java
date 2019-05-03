package com.telekom.service.api;

import com.telekom.model.dto.ContractDto;

import javax.mail.MessagingException;

public interface MailService {

    void sendMessageWithAttachment( String subject, String text, ContractDto contract) throws MessagingException;
}
