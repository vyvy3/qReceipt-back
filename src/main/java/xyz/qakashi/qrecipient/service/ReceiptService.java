package xyz.qakashi.qrecipient.service;

import org.springframework.core.io.Resource;
import xyz.qakashi.qrecipient.web.dto.ReceiptGenerateDto;
import xyz.qakashi.qrecipient.web.dto.qReceiptViewDto;

import java.util.List;

public interface ReceiptService {
    qReceiptViewDto generateReceipt (ReceiptGenerateDto dto, String authorName);

    List<qReceiptViewDto> getAllByAuthor(String author);
}
