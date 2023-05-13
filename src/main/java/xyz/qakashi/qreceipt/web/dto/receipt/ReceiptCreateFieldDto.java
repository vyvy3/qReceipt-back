package xyz.qakashi.qreceipt.web.dto.receipt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptCreateFieldDto {
    private String name;

    private Double price;

    private Integer quantity;

    private Double totalPrice;
}
