package xyz.qakashi.qrecipient.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.qakashi.qrecipient.util.Constants;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = Constants.DATABASE_PREFIX + "receipt_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptTemplate extends BaseEntity<Long> {
    @Column(name = "value")
    private String value;

    @Column(name = "position")
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "form_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ReceiptForm receiptForm;

    @Column(name = "form_id")
    private Long formId;

    @OneToMany(mappedBy = "template")
    private Set<ReceiptTemplateParam> params = new HashSet<>();

    public List<String> getParamNames() {
        return params.stream()
                .map(ReceiptTemplateParam::getName)
                .collect(Collectors.toList());
    }
}
