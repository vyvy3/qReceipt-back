package xyz.qakashi.qreceipt.service;

import xyz.qakashi.qreceipt.web.dto.qReceiptViewDto;

import java.util.List;
import java.util.Map;

public interface ReceiptService {
    qReceiptViewDto generateReceipt (Map<String, Double> products, String authorName);

    List<qReceiptViewDto> getAllByAuthor(String author);
}
