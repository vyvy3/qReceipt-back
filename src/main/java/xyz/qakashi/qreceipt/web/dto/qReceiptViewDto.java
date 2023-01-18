package xyz.qakashi.qreceipt.web.dto;

import lombok.Data;
import xyz.qakashi.qreceipt.domain.qReceipt;

import java.time.ZonedDateTime;

@Data
public class qReceiptViewDto {
    private Long id;

    private String fileName;

    private String extension;

    private ZonedDateTime printDate;

    private String author;

    public qReceiptViewDto(qReceipt qreceipt) {
        this.id = qreceipt.getId();
        this.fileName = qreceipt.getFileName();
        this.extension = qreceipt.getExtension().name();
        this.printDate = qreceipt.getPrintDate();
        this.author = qreceipt.getAuthor();
    }
}
