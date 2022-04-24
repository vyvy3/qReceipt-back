package xyz.qakashi.qrecipient.domain;

import lombok.*;
import xyz.qakashi.qrecipient.domain.enums.Gender;
import xyz.qakashi.qrecipient.util.Constants;

import javax.persistence.*;
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

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
