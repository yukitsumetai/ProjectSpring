package com.telekom.service.impl;

import com.telekom.model.dto.ClientDto;
import com.telekom.service.api.ImageRecognitionService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageRecognitionImpl implements ImageRecognitionService {

    @Autowired
    Logger logger;

    /**
     * Performs OCR for a given picture. OCR returns string with recognized text
     *
     * @param client
     * @param path   path to a picture
     */
    @Override
    public void doOCR(ClientDto client, String path) {
        Tesseract tesseract = new Tesseract();
        String text = "";
        logger.info("PerformingOCR");
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        try {
            tesseract.setDatapath(tessDataFolder.getAbsolutePath());
            logger.info("Set path done");
            tesseract.setLanguage("eng");
            logger.info("Set language");
            logger.info(path);
            File file = new File(path);
            logger.info("Set file");
            text = tesseract.doOCR(file);
            logger.info("Parsed Text " + text);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        parseData(client, text);
        logger.info("Parsed data ");
    }

    /**
     * Breakes text into lines
     *
     * @param client
     * @param text
     */
    private void parseData(ClientDto client, String text) {
        logger.info("Parsing data");
        String[] parts = text.split("\n");
        if (parts.length < 2) return;
        List<String> result = new ArrayList();
        for (int i = (parts.length - 1); i >= 0; i--) {
            if (parts[i].length() > 3) result.add(parts[i].trim());
        }
        if (result.size() < 2) return;

        setName(client, result);
        String text2 = result.get(0).trim();
        text2 = text2.replaceAll("[^A-Za-z0-9]+", "");
        ;
        setPassportBirthday(client, text2);

    }

    /**
     * Parses Passport and Birthday data
     *
     * @param client
     * @param text2
     */
    private void setPassportBirthday(ClientDto client, String text2) {
        try {
            client.setPassport(Integer.parseInt(text2.substring(0, 9)));
        } catch (NumberFormatException e) {
            logger.info("Passport format wrong" + text2.substring(0, 9));
        } catch (IndexOutOfBoundsException e) {
            logger.info("Passport format wrong" + text2);
        }
        String birthday = "";


        try {
            birthday = text2.substring(13, 19);
            int year = Integer.parseInt(birthday.substring(0, 2));
            if (year < 20) year = 2000 + year;
            else year = 1900 + year;
            client.setBirthday(year + "-" + birthday.substring(2, 4) + "-" + birthday.substring(4, 6));
        } catch (NumberFormatException e) {
            logger.info("Birthday format wrong: " + birthday);
        } catch (IndexOutOfBoundsException e) {
            logger.info("Passport format wrong: " + text2);
        }
    }

    /**
     * Parses Name and Surname data
     *
     * @param client
     * @param result
     */
    private void setName(ClientDto client, List<String> result) {
        String text1 = result.get(1).substring(5);
        for (String s : result) {
            String[] parts = s.split(" ");
            int maxLength = 0;
            String text = "";
            for (String s2 : parts) {
                if (s2.length() > maxLength) {
                    maxLength = s2.length();
                    text = s2;
                }

            }
            if (text1.contains(text)) {
                String tmp = text.substring(0, 1) + text.substring(1).toLowerCase();
                if (text1.substring(4).contains(text)) {
                    client.setName(tmp);
                } else client.setSurname(tmp);
            }

        }
        if (client.getSurname() == null || client.getName() == null) {
            logger.info("Name and/or surname was not recognized correctly - " + text1);
        }
    }
}

