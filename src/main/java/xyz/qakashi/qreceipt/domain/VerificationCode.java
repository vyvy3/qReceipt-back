package xyz.qakashi.qreceipt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.qakashi.qreceipt.domain.enums.VerificationType;

import javax.persistence.*;
import java.time.ZonedDateTime;

import static xyz.qakashi.qreceipt.util.Constants.CODE_LIFETIME_IN_SECONDS;
import static xyz.qakashi.qreceipt.util.Constants.DATABASE_PREFIX;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DATABASE_PREFIX + "verification_code")
public class VerificationCode extends BaseEntity<Long> {
    @Column(name = "verification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VerificationType verificationType;

    @Column(name = "code")
    private String code;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "send_time", nullable = false)
    private ZonedDateTime sendTime;

    @Column(name = "blocked_until")
    private ZonedDateTime blockedUntil;

    @Column(name = "number_of_tries", nullable = false, columnDefinition = "integer default 0")
    private int numberOfTries = 0;

    @Column(name = "confirmed", nullable = false, columnDefinition = "boolean default false")
    private boolean confirmed = false;

    public void clearCode() {
        this.code = null;
    }

    public ZonedDateTime getExpirationTime() {
        return this.sendTime.plusSeconds(CODE_LIFETIME_IN_SECONDS);
    }
}
