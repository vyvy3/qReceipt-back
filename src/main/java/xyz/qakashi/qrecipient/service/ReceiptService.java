package xyz.qakashi.qrecipient.service;

import org.springframework.core.io.Resource;
import xyz.qakashi.qrecipient.web.dto.ReceiptGenerateDto;

public interface ReceiptService {
    String generateReceipt (ReceiptGenerateDto dto);
}
