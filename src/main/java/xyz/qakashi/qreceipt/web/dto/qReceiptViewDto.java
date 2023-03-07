package xyz.qakashi.qreceipt.web.dto;

import lombok.Data;
import xyz.qakashi.qreceipt.domain.qReceipt;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class qReceiptViewDto {
    private Long id;

    private UUID fileUUID;

    private ZonedDateTime printDate;

    private String author;

    public qReceiptViewDto(qReceipt qreceipt) {
        this.id = qreceipt.getId();
        this.fileUUID = qreceipt.getFileUUID();
        this.printDate = qreceipt.getPrintDate();
        this.author = qreceipt.getAuthor();
    }
}
