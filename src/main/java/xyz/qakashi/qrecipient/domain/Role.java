package xyz.qakashi.qrecipient.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.qakashi.qrecipient.util.Constants;

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

    public static final Long ROLE_USER = 1L;
    public static final Long ROLE_ADMIN = 2L;
}