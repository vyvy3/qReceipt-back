package xyz.qakashi.qreceipt.service;

import org.springframework.http.ResponseEntity;
import xyz.qakashi.qreceipt.web.dto.qReceiptViewDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReceiptService {
//    qReceiptViewDto generateReceipt (Map<String, Double> products, String authorName);

    List<qReceiptViewDto> getAllByAuthor(String author);

    List<qReceiptViewDto> getAllByOwner(String author);

    UUID createReceipt(Map<String, Double> products, String authorName);

    ResponseEntity<byte[]> getReceiptQR(UUID id);

    void assignReceipt(String assignTo, UUID receiptId);
}
