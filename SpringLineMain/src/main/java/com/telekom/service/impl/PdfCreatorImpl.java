package com.telekom.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.telekom.model.dto.ContractDto;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.TariffDto;
import com.telekom.service.api.PdfCreator;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.davidmoten.text.utils.WordWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

@Service
public class PdfCreatorImpl implements PdfCreator {
    @Autowired
    Logger logger;

    private BaseFont bfBold;
    private BaseFont bf;
    private int pageNumber = 0;

    @Override
    public String createPdf(ContractDto contract) {
        Document document = new Document();
        String pdfName = null;
        try {
            logger.info("Creating pdf");
            //Properties props = System.getProperties();
            pdfName = ".\\..\\standalone\\tmp\\invoices\\"+ contract.getPhoneNumber() + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfName));
            initializeFonts();

            document.addAuthor("Spring Line");
            document.addCreationDate();
            document.addProducer();
            document.addCreator("springLine.com");
            document.addTitle("Invoice");
            document.setPageSize(PageSize.LETTER);
            document.open();

            PdfContentByte cb = writer.getDirectContent();
            logger.info("Creating page");

            Set<OptionDto> options = contract.getOptions();
            TariffDto tariff = contract.getTariff();
            String phoneNumber = contract.getPhoneNumber();
            String name = contract.getClient().getName() + " " + contract.getClient().getSurname();
            int i = 1;
            boolean beginPage = false;
            generateLayout(document, cb);
            generateHeader(document, cb, name, phoneNumber);
            int y = 615;
            generateSubheader(document, cb, y, "TARIFF");
            y = y - 15;

            String tariffName = WordWrap.from(tariff.getName())
                    .maxWidth(20)
                    .insertHyphens(false) // true is the default
                    .wrap();

            String tariffDescription = WordWrap.from(tariff.getName())
                    .maxWidth(60)
                    .insertHyphens(false) // true is the default
                    .wrap();


            generateDetail(document, cb, i, y, tariffName, tariffDescription, tariff.getPrice(), 0);
            y = cellLength(tariffName, tariffDescription, y);
            if (y < 50) {
                printPageNumber(cb);
                document.newPage();
                beginPage = true;
            }

            if (options.size() > 0) {
                generateSubheader(document, cb, y, "OPTIONS");
                y = y - 15;
            }
            for (OptionDto o : options) {
                i++;
                if (beginPage) {
                    beginPage = false;
                    generateLayout(document, cb);
                    generateHeader(document, cb, name, phoneNumber);
                    y = 615;
                }
                String optionName = WordWrap.from(o.getName())
                        .maxWidth(20)
                        .insertHyphens(false) // true is the default
                        .wrap();

                String optionDescription = WordWrap.from(o.getDescription())
                        .maxWidth(60)
                        .insertHyphens(false) // true is the default
                        .wrap();

                generateDetail(document, cb, i, y, optionName, optionDescription, o.getPriceMonthly(), o.getPriceOneTime());
                y = cellLength(optionName, optionDescription, y);
                if (y < 50) {
                    printPageNumber(cb);
                    document.newPage();
                    beginPage = true;
                }
            }

            generateTotal(document, cb, y, "TOTAL", contract.getPrice(), contract.getPriceOneTime());

            document.close();
            writer.close();
        } catch (FileNotFoundException e) {
            logger.info("File not found");
        } catch (DocumentException e) {
            logger.info("Document exception");
        }
        return pdfName;
    }

    private void initializeFonts() {
        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateLayout(Document doc, PdfContentByte cb) {

        try {
            cb.setLineWidth(0);
            // Invoice Header box layout
            cb.rectangle(420, 700, 150, 60);
            cb.moveTo(420, 720);
            cb.lineTo(570, 720);
            cb.moveTo(420, 740);
            cb.lineTo(570, 740);
            cb.moveTo(480, 700);
            cb.lineTo(480, 760);
            cb.stroke();
            // Invoice Header box Text Headings
            createHeadings(cb, 422, 743, "Client Name");
            createHeadings(cb, 422, 723, "Telephone Nr");
            createHeadings(cb, 422, 703, "Invoice Date");

            // Invoice Detail box Text Headings
            createHeadings(cb, 22, 633, "#");
            createHeadings(cb, 52, 633, "Name");
            createHeadings(cb, 152, 633, "Description");
            createHeadings(cb, 432, 633, "Monthly Price, $");
            createHeadings(cb, 502, 633, "One time Price, $");

            //add the images
            Image companyLogo = Image.getInstance("C:\\Users\\ekochuro\\IdeaProjects\\ProjectSpring\\SpringLineMain\\src\\main\\resources\\email\\logo.png");
            companyLogo.setAbsolutePosition(25, 700);
            companyLogo.scalePercent(25);
            doc.add(companyLogo);

        } catch (DocumentException dex) {
            logger.info("Exception", dex);
        } catch (Exception ex) {
            logger.info("Exception", ex);
        }
    }

    private void generateHeader(Document doc, PdfContentByte cb, String name, String phoneNumber) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        String date = formatter.format(today);
        try {
            createHeadings(cb, 200, 750, "Spring Line");
            createHeadings(cb, 200, 720, "Mannheimerstr 39");
            createHeadings(cb, 200, 705, "Bonn, 67456");
            createHeadings(cb, 200, 690, "Germany");

            createHeadings(cb, 482, 743, name);
            createHeadings(cb, 482, 723, phoneNumber);
            createHeadings(cb, 482, 703, date);
        } catch (Exception ex) {
            logger.info("Exception ", ex);
        }
    }

    private void generateDetail(Document doc, PdfContentByte cb, int index, int y, String name, String description, double priceMonthly, double priceOneTime) {
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            createContent(cb, 22, y, String.valueOf(index), PdfContentByte.ALIGN_LEFT);
            String nameArray[] = name.split("\n");
            int z = y;
            for (String s : nameArray) {
                createContent(cb, 52, z, s, PdfContentByte.ALIGN_LEFT);
                z -= 15;
            }

            String descriptionArray[] = description.split("\n");
            z = y;
            for (String s : descriptionArray) {
                createContent(cb, 152, z, s, PdfContentByte.ALIGN_LEFT);
                z -= 15;
            }
            createContent(cb, 498, y, df.format(priceMonthly), PdfContentByte.ALIGN_RIGHT);
            createContent(cb, 568, y, df.format(priceOneTime), PdfContentByte.ALIGN_RIGHT);
        } catch (Exception ex) {
            logger.info("Exception ", ex);
        }
    }

    private void generateSubheader(Document doc, PdfContentByte cb, int y, String name) {
        try {
            createContent(cb, 52, y, name, PdfContentByte.ALIGN_LEFT);
        } catch (Exception ex) {
            logger.info("Exception ", ex);
        }
    }

    private void generateTotal(Document doc, PdfContentByte cb, int y, String name, double priceMonthly, double priceOneTime) {
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            createContent(cb, 52, y, name, PdfContentByte.ALIGN_LEFT);
            createContent(cb, 498, y, df.format(priceMonthly), PdfContentByte.ALIGN_RIGHT);
            createContent(cb, 568, y, df.format(priceOneTime), PdfContentByte.ALIGN_RIGHT);
        } catch (Exception ex) {
            logger.info("Exception ", ex);
        }
    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    private void printPageNumber(PdfContentByte cb) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Page No. " + (pageNumber + 1), 570, 25, 0);
        cb.endText();
        pageNumber++;
    }

    private void createContent(PdfContentByte cb, float x, float y, String text, int align) {
        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.showTextAligned(align, text, x, y, 0);
        cb.endText();
    }

    private int cellLength(String name, String description, int y) {
        if (name.length() > 40 || description.length() > 120) y = y - 45;
        else if (name.length() > 20 || description.length() > 60) y = y - 30;
        else y = y - 15;
        return y;
    }

}
