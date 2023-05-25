package xyz.qakashi.qreceipt.domain;

import lombok.*;
import xyz.qakashi.qreceipt.util.Constants;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.DATABASE_PREFIX + "gender")
public class Gender {
    private static final String MALE = "MALE";
    private static final String FEMALE = "FEMALE";
    private static final String OTHER = "OTHER";

    @Id
    private String slug;
}
