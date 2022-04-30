package xyz.qakashi.qrecipient.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.qakashi.qrecipient.domain.enums.FileExtension;
import xyz.qakashi.qrecipient.util.Constants;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = Constants.DATABASE_PREFIX + "receipt")
@NoArgsConstructor
@AllArgsConstructor
public class qReceipt extends BaseEntity<Long> {

    @Column(name = "file_name")
    private String fileName;

    @Enumerated(EnumType.STRING)
    private FileExtension extension;

    @Column(name = "print_date")
    private ZonedDateTime printDate;

    private String author;
}
