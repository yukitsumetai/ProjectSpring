package com.telekom.utils.api;

import com.telekom.model.dto.ContractDto;

import javax.mail.MessagingException;

public interface MailService {

    boolean sendMessageWithAttachment(Boolean existing, ContractDto contract);
}
