package xyz.qakashi.qreceipt.service;

import org.springframework.http.ResponseEntity;
import xyz.qakashi.qreceipt.web.dto.receipt.ReceiptRegistryDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReceiptService {
//    qReceiptViewDto generateReceipt (Map<String, Double> products, String authorName);

    List<ReceiptRegistryDto> getAllByCashier(String cashier);

    List<ReceiptRegistryDto> getAllByOwner(String ownerLogin);

    UUID createReceipt(Map<String, Double> products, String cashier);

    ResponseEntity<byte[]> getReceiptQR(UUID id);

    void assignReceipt(String assignTo, UUID receiptId);
}
