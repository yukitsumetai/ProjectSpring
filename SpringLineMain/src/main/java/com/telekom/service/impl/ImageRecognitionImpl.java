package com.telekom.service.impl;

import com.telekom.model.dto.ClientDto;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ImageRecognitionImpl {

    @Autowired
    Logger logger;

    public void doOCR(ClientDto client, String path) {
        Tesseract tesseract = new Tesseract();
        String text = "";
        try {
            logger.info("PerformingOCR");
            tesseract.setDatapath("C:/Users/ekochuro/IdeaProjects/ProjectSpring/SpringLineMain/src/main/resources/tessTrainData");
            tesseract.setLanguage("eng");
            text = tesseract.doOCR(new File("C:/Users/ekochuro/IdeaProjects/ProjectSpring/SpringLineMain/src/main/resources/tess2/pass7.jpg"));
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        parseData(client, text);
    }

    private void parseData(ClientDto client, String text) {
        logger.info("Parsing data");
        String[] parts = text.split("\n");
        List<String> result = new ArrayList();
        for (int i = (parts.length - 1); i >= 0; i--) {
            if (parts[i].length() > 3) result.add(parts[i]);
        }
        setName(client, result);

        String text2 = result.get(0);
        try {
            client.setPassport(Integer.parseInt(text2.substring(0, 9)));
        } catch (NumberFormatException e) {
            logger.info("Passport format wrong" + text2.substring(0, 9));
        }
        String birthday = text2.substring(13, 19);
        try {
            Integer year = Integer.parseInt(birthday.substring(0, 2));
            if (year<20) year=2000+year;
                    else year=1900+year;
            Integer month = Integer.parseInt(birthday.substring(2, 4));
            Integer day = Integer.parseInt(birthday.substring(4, 5));
            client.setBirthday(year+"-"+birthday.substring(2, 4)+"-"+birthday.substring(4, 6));
        } catch (NumberFormatException e) {
            logger.info("Birthday format wrong" + birthday);
        }

    }


    private void setName(ClientDto client, List<String> result) {
        String text1 = result.get(1).substring(5);
        boolean name = true;
        for (String s : result) {
            if (text1.contains(s)) {
                String tmp = s.substring(0, 1) + s.substring(1).toLowerCase();
                if (name) {
                    client.setName(tmp);
                    name = false;
                } else client.setSurname(tmp);
            }
        }
        if (client.getSurname() == null) {
            logger.info("Name and/or surname was not recognized correctly");
            client.setName(null);
        }
    }
}

