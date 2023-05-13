package xyz.qakashi.qreceipt.web.dto.receipt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import xyz.qakashi.qreceipt.domain.Organization;
import xyz.qakashi.qreceipt.domain.User;
import xyz.qakashi.qreceipt.domain.qReceipt;
import xyz.qakashi.qreceipt.web.dto.organization.OrganizationRegistryDto;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDetailedDto {

    private OrganizationRegistryDto organization;

    private List<ReceiptCreateFieldDto> products = new ArrayList<>();

    private ZonedDateTime createdDate;

    private Double total = 0D;

    public ReceiptDetailedDto(qReceipt qreceipt) {
        this.createdDate = qreceipt.getCreatedDate();
        this.total = qreceipt.getTotalSum();
        this.organization = new OrganizationRegistryDto(qreceipt.getOrganization());
        this.products = qreceipt.getJson();
    }
}
