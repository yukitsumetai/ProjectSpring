package com.telekom.service.api;

import com.telekom.model.dto.ClientDto;
import net.sourceforge.tess4j.TesseractException;

public interface ImageRecognitionService {
   void doOCR(ClientDto client, String path);
}
