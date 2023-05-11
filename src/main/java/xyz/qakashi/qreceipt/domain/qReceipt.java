package xyz.qakashi.qreceipt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import xyz.qakashi.qreceipt.util.Constants;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = Constants.DATABASE_PREFIX + "receipt")
@NoArgsConstructor
@AllArgsConstructor
public class qReceipt {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "print_date")
    private ZonedDateTime printDate;

    @Column(name = "author")
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private User owner;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "json", columnDefinition = "text")
    private String json;

    @CreationTimestamp
    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "total_sum")
    private Double totalSum = 0D;
}
