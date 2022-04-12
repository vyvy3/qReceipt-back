package xyz.qakashi.qrecipient.domain;

import lombok.*;
import xyz.qakashi.qrecipient.util.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.DATABASE_PREFIX + "user")
public class User extends BaseEntity<Long> {
    @Column(name = "username", columnDefinition = "VARCHAR")
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR")
    private String password;

    @Column(name = "verified_email")
    @Builder.Default
    private Boolean verified = false;


    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
