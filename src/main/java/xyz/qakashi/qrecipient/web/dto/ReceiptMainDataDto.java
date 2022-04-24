package xyz.qakashi.qrecipient.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiptMainDataDto {
    private String rowNumber;

    private String key;

    private String value;
}
