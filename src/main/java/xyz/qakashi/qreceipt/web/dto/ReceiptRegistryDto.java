package xyz.qakashi.qreceipt.web.dto;

import lombok.*;
import xyz.qakashi.qreceipt.domain.qReceipt;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptRegistryDto {
    private UUID id;
    private ZonedDateTime createdDate;
    private Double total;
    private OrganizationRegistryDto organization;

    public ReceiptRegistryDto(qReceipt qreceipt) {
        this.id = qreceipt.getId();
        this.createdDate = qreceipt.getCreatedDate();
        this.total = qreceipt.getTotalSum();
        this.organization = new OrganizationRegistryDto(qreceipt.getOrganization());
    }
}
