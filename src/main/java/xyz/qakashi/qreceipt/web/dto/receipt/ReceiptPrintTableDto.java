package xyz.qakashi.qreceipt.web.dto.receipt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiptPrintTableDto {
    private String rowNumber;

    private String name;

    private String price;

    private String quantity;

    private String totalPrice;
}
