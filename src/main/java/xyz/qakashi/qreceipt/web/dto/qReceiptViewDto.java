package xyz.qakashi.qreceipt.web.dto;

import lombok.Data;
import xyz.qakashi.qreceipt.domain.qReceipt;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class qReceiptViewDto {
    private UUID id;

    private ZonedDateTime createdDate;

    private Double total;

    public qReceiptViewDto(qReceipt qreceipt) {
        this.id = qreceipt.getId();
        this.createdDate = qreceipt.getCreatedDate();
        this.total = qreceipt.getTotalSum();
    }
}
