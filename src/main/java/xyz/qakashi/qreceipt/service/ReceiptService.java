package xyz.qakashi.qreceipt.service;

import org.springframework.core.io.Resource;
import xyz.qakashi.qreceipt.web.dto.ReceiptGenerateDto;
import xyz.qakashi.qreceipt.web.dto.qReceiptViewDto;

import java.util.List;

public interface ReceiptService {
    qReceiptViewDto generateReceipt (ReceiptGenerateDto dto, String authorName);

    List<qReceiptViewDto> getAllByAuthor(String author);
}
