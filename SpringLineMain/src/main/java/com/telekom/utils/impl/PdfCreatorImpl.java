package com.telekom.utils.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.telekom.model.dto.ContractDto;
import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.TariffDto;
import com.telekom.utils.api.PdfCreator;
import org.apache.log4j.Logger;
import org.davidmoten.text.utils.WordWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Service
public class PdfCreatorImpl implements PdfCreator {
    @Autowired
    Logger logger;

    private BaseFont bfBold;
    private BaseFont bf;
    private int pageNumber = 0;

    /**
     * Creating file and filling it with details from contract
     * @param contract
     * @return
     */
    @Override
    public String createPdf(ContractDto contract) {
        Document document = new Document();
        String pdfName = ".\\standalone\\tmp\\"+ contract.getPhoneNumber() + ".pdf";
        logger.info("Creating pdf " +pdfName);
        try {
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
            generateHeader(cb, name, phoneNumber);
            int y = 615;
            generateSubheader(cb, y, "TARIFF");
            y = y - 15;

            String tariffName = WordWrap.from(tariff.getName())
                    .maxWidth(20)
                    .insertHyphens(false) // true is the default
                    .wrap();

            String tariffDescription = WordWrap.from(tariff.getName())
                    .maxWidth(60)
                    .insertHyphens(false) // true is the default
                    .wrap();


            generateDetail(cb, i, y, tariffName, tariffDescription, tariff.getPrice(), 0);
            y = rowWidth(tariffName, tariffDescription, y);
            if (y < 50) {
                printPageNumber(cb);
                document.newPage();
                beginPage = true;
            }

            if (options!=null && options.size() > 0) {
                generateSubheader(cb, y, "OPTIONS");
                y = y - 15;
            }
            for (OptionDto o : options) {
                i++;
                if (beginPage) {
                    beginPage = false;
                    generateLayout(document, cb);
                    generateHeader(cb, name, phoneNumber);
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

                generateDetail(cb, i, y, optionName, optionDescription, o.getPriceMonthly(), o.getPriceOneTime());
                y = rowWidth(optionName, optionDescription, y);
                if (y < 50) {
                    printPageNumber(cb);
                    document.newPage();
                    beginPage = true;
                }
            }

            generateTotal(cb, y, contract.getPrice(), contract.getPriceOneTime());

            document.close();
            writer.close();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
        return pdfName;
    }

    /**
     * Initializing fonts
     */
    private void initializeFonts() {
        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
           logger.error(e.getMessage(),e);
        }
    }

    /**
     * Generating PDF's layout
     * @param doc
     * @param cb
     */
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

            Image companyLogo = Image.getInstance("C:\\Users\\ekochuro\\IdeaProjects\\ProjectSpring\\SpringLineMain\\src\\main\\resources\\email\\springLineLogo.png");
            companyLogo.setAbsolutePosition(25, 700);
            companyLogo.scalePercent(25);
            doc.add(companyLogo);

        } catch (Exception dex) {
            logger.info(dex.getMessage(), dex);
        }
    }

    /**
     * Generating header of PDF
     * @param cb
     * @param name
     * @param phoneNumber
     */
    private void generateHeader(PdfContentByte cb, String name, String phoneNumber) {
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
            logger.info(ex.getMessage(), ex);
        }
    }

    /**
     * Generating row of a table
     * @param cb
     * @param index
     * @param y
     * @param name
     * @param description
     * @param priceMonthly
     * @param priceOneTime
     */
    private void generateDetail(PdfContentByte cb, int index, int y, String name, String description, double priceMonthly, double priceOneTime) {
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            createContent(cb, 22, y, String.valueOf(index), PdfContentByte.ALIGN_LEFT);
            String[] nameArray = name.split("\n");
            int z = y;
            for (String s : nameArray) {
                createContent(cb, 52, z, s, PdfContentByte.ALIGN_LEFT);
                z -= 15;
            }

            String[] descriptionArray = description.split("\n");
            z = y;
            for (String s : descriptionArray) {
                createContent(cb, 152, z, s, PdfContentByte.ALIGN_LEFT);
                z -= 15;
            }
            createContent(cb, 498, y, df.format(priceMonthly), PdfContentByte.ALIGN_RIGHT);
            createContent(cb, 568, y, df.format(priceOneTime), PdfContentByte.ALIGN_RIGHT);
        } catch (Exception ex) {
            logger.info(ex.getMessage(), ex);
        }
    }

    /**
     * Generating subheaders in table (Tariff, Options)
     * @param cb
     * @param y
     * @param name
     */
    private void generateSubheader(PdfContentByte cb, int y, String name) {
        try {
            createContent(cb, 52, y, name, PdfContentByte.ALIGN_LEFT);
        } catch (Exception ex) {
            logger.info(ex.getMessage(), ex);
        }
    }

    /**
     * Generating total row
     * @param cb
     * @param y
     * @param priceMonthly
     * @param priceOneTime
     */
    private void generateTotal(PdfContentByte cb, int y, double priceMonthly, double priceOneTime) {
        DecimalFormat df = new DecimalFormat("0.00");
        cb.setFontAndSize(bfBold, 8);
        try {
            createContent(cb, 52, y, "TOTAL", PdfContentByte.ALIGN_LEFT);
            createContent(cb, 498, y, df.format(priceMonthly), PdfContentByte.ALIGN_RIGHT);
            createContent(cb, 568, y, df.format(priceOneTime), PdfContentByte.ALIGN_RIGHT);
        } catch (Exception ex) {
            logger.info(ex.getMessage(), ex);
        }
    }

    /**
     * Generate table headings
     * @param cb
     * @param x
     * @param y
     * @param text
     */
    private void createHeadings(PdfContentByte cb, float x, float y, String text) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    /**
     * Generate page number
     * @param cb
     */
    private void printPageNumber(PdfContentByte cb) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Page No. " + (pageNumber + 1), 570, 25, 0);
        cb.endText();
        pageNumber++;
    }

    /**
     * Create cell content
     * @param cb
     * @param x
     * @param y
     * @param text
     * @param align
     */
    private void createContent(PdfContentByte cb, float x, float y, String text, int align) {
        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.showTextAligned(align, text, x, y, 0);
        cb.endText();
    }

    /**
     * Counting width of the row for given content
     * @param name
     * @param description
     * @param y
     * @return
     */
    private int rowWidth(String name, String description, int y) {
        if (name.length() > 40 || description.length() > 120) y = y - 45;
        else if (name.length() > 20 || description.length() > 60) y = y - 30;
        else y = y - 15;
        return y;
    }

}
