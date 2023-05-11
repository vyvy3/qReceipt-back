package xyz.qakashi.qreceipt.domain;

import lombok.*;
import xyz.qakashi.qreceipt.util.Constants;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.DATABASE_PREFIX + "user")
public class User extends BaseEntity<Long> {
    @Column(name = "login", columnDefinition = "VARCHAR")
    private String login;

    @Column(name = "password", columnDefinition = "VARCHAR")
    private String password;

    @Column(name = "verified", nullable = false, columnDefinition = "boolean default false")
    private boolean verified = false;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "picture")
    private UUID picture;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    private Organization organization;

    @Column(name = "organization_id")
    private Long organizationId;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
}
