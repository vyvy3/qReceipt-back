package xyz.qakashi.qrecipient.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.qakashi.qrecipient.util.Constants;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.DATABASE_PREFIX + "user")
public class User extends BaseEntity<Long> {
    private String phone;
    private String name;
    private String password;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
