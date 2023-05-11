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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.DATABASE_PREFIX + "organization")
public class Organization extends BaseEntity<Long> {
    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "picture")
    private UUID picture;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    Set<User> users = new HashSet<>();
}
