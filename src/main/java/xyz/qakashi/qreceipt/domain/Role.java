package xyz.qakashi.qreceipt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.qakashi.qreceipt.util.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Constants.DATABASE_PREFIX + "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity<Long> {
    @Column(name = "name")
    private String name;

    public static final Long USER = 1L;
    public static final Long ADMIN = 2L;
    public static final Long ORG_MANAGER = 3L;
    public static final Long API_USER = 4L;
}