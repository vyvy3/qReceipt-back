package xyz.qakashi.qrecipient.domain;

import lombok.*;
import xyz.qakashi.qrecipient.util.Constants;

import javax.persistence.*;

@Entity
@Table(name = Constants.DATABASE_PREFIX + "email_verification")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerification extends BaseEntity<Long> {
    @Column(name = "verificationKey")
    private String verificationKey;

    @Column(name = "code")
    private String code;

    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}

