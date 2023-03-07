package xyz.qakashi.qreceipt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class qReceipt extends BaseEntity<Long> {

    @Column(name = "file_uuid")
    private UUID fileUUID;

    @Column(name = "print_date")
    private ZonedDateTime printDate;

    private String author;
}
