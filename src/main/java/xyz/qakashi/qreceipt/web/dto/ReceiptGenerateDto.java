package xyz.qakashi.qreceipt.web.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ReceiptGenerateDto {
    private Long formId;

    private Map<String, String> values = new HashMap<>();

}
