package com.telekom.utils.impl;

import com.telekom.model.dto.ContractDto;
import com.telekom.service.api.ContractService;
import com.telekom.utils.api.MailService;
import com.telekom.utils.api.PdfCreator;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PdfCreator pdfCreator;
    @Autowired
    private ContractService contractService;
    @Autowired
    private VelocityEngine velocityEngine;

    /**
     * Sends an email with PDF wit contract information
     *
     * @param newClient if client is new or not. Welcome messages are sent to new clients and updates for existing
     * @param contract
     * @throws MessagingException
     */
    @Override
    public boolean sendMessageWithAttachment(Boolean newClient, ContractDto contract) {
        logger.info("Sending email with invoice");
        MimeMessage message = mailSender.createMimeMessage();
        String path = pdfCreator.createPdf(contract);
        logger.info(path);
        File file = new File(path);
        try{
            String absolute = file.getCanonicalPath();
            logger.info("Canonical " +absolute);}
        catch (IOException e){}
        String absolute2 = file.getAbsolutePath();
        logger.info("Absolute " +absolute2);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String email = contract.getClient().getEmail();
            helper.setTo(email);

            String subject;
            if (newClient) subject = "Welcome to Spring Line";
            else subject = "Update from Spring Line";
            helper.setSubject(subject);

            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();

            Template template;
            if (newClient) template = ve.getTemplate("/email/Welcome.vm");
            else template = ve.getTemplate("/email/Update.vm");

            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("name", contract.getClient().getName() + " " + contract.getClient().getSurname());
            velocityContext.put("phone", contract.getPhoneNumber());

            StringWriter stringWriter = new StringWriter();
            template.merge(velocityContext, stringWriter);
            helper.setText(stringWriter.toString(), true);


            helper.addAttachment("Invoice.pdf", file);

            mailSender.send(message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            //file.delete();
        }
        return true;
    }


}
