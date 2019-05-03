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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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

    public void send(String emailTo, String subject, String message) {
        logger.info("Sending email");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("ekaterina.kochurova@t-systems.com");
        mail.setSubject("Welcome to Spring Line");
        mail.setText("Test");
        mailSender.send(mail);
        logger.info("Email sent");
    }

    @Override
    public void sendMessageWithAttachment(String subject, String text, ContractDto contract) throws MessagingException {
        logger.info("Sending email with invoice");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        contract.getClient().getEmail();
        helper.setTo("ekaterina.kochurova@t-systems.com");
        helper.setSubject(subject);

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template template = ve.getTemplate("/email/Welcome.vm");

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("firstName", "Yashwant");
        velocityContext.put("lastName", "Chavan");
        velocityContext.put("location", "Pune");

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);
        helper.setText(stringWriter.toString());


        String path = pdfCreator.createPdf(contract);
        FileSystemResource file = new FileSystemResource(new File(path));
        helper.addAttachment(".pdf", file);
        mailSender.send(message);

    }


}
