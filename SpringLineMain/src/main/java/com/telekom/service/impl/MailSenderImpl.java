package com.telekom.service.impl;

import com.telekom.model.dto.ContractDto;
import com.telekom.service.api.ContractService;
import com.telekom.service.api.MailService;
import com.telekom.service.api.PdfCreator;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import java.io.*;

@Service
public class MailSenderImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Logger logger;
    @Autowired
    PdfCreator pdfCreator;
    @Autowired
    ContractService contractService;
    @Autowired
    VelocityEngine velocityEngine;


    @Override
    public void sendMessageWithAttachment(Boolean newClient, ContractDto contract) throws MessagingException {
        logger.info("Sending email with invoice");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String email = contract.getClient().getEmail();
        helper.setTo("yukitsumetai@yandex.ru");

        String subject;
        if(newClient) subject = "Welcome to Spring Line";
        else  subject = "Update from Spring Line";
        helper.setSubject(subject);

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        Template template;
        if(newClient) template = ve.getTemplate("/email/Welcome.vm");
        else template = ve.getTemplate("/email/Update.vm");

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("name", contract.getClient().getName() + " " + contract.getClient().getSurname());
        velocityContext.put("phone", contract.getPhoneNumber());

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);
        helper.setText(stringWriter.toString(), true);


        String path = pdfCreator.createPdf(contract);
        File file = new File(path);
        helper.addAttachment("Invoice.pdf", file);

        mailSender.send(message);
        file.delete();
    }


}
