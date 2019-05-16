package com.telekom.utils.api;

import com.telekom.model.dto.ContractDto;

public interface PdfCreator {
    String createPdf(ContractDto contract);
}
