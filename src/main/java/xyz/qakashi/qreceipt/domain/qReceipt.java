package xyz.qakashi.qreceipt.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import xyz.qakashi.qreceipt.util.Constants;
import xyz.qakashi.qreceipt.web.dto.receipt.ReceiptCreateFieldDto;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = Constants.DATABASE_PREFIX + "receipt")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@NoArgsConstructor
@AllArgsConstructor
public class qReceipt {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier_id", insertable = false, updatable = false)
    private User cashier;

    @Column(name = "cashier_id")
    private Long cashierId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private User owner;

    @Column(name = "owner_id")
    private Long ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    private Organization organization;

    @Column(name = "organization_id")
    private Long organizationId;

    @Type(type = "jsonb")
    @Column(name = "json", columnDefinition = "jsonb")
    private List<ReceiptCreateFieldDto> json = new ArrayList<>();

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "total_sum")
    private Double totalSum = 0D;
}
