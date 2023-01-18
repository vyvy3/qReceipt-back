package xyz.qakashi.qreceipt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.qakashi.qreceipt.util.Constants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Constants.DATABASE_PREFIX + "receipt_form")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptForm extends BaseEntity<Long> {
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "receiptForm")
    @OrderBy("position")
    private List<ReceiptTemplate> templates = new ArrayList<>();
}
