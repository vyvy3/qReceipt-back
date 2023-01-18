package xyz.qakashi.qreceipt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.qakashi.qreceipt.util.Constants;

import javax.persistence.*;

@Entity
@Table(name = Constants.DATABASE_PREFIX + "template_param")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptTemplateParam extends BaseEntity<Long> {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ReceiptTemplate template;

    @Column(name = "template_id")
    private Long templateId;
}
