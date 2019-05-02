package com.telekom.service.api;

import com.telekom.model.dto.ContractDto;

public interface PdfCreator {
    String createPdf(ContractDto contract);
}
