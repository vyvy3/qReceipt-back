package xyz.qakashi.qreceipt.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

import static xyz.qakashi.qreceipt.util.Constants.DATABASE_PREFIX;

@Table(name = DATABASE_PREFIX + "file_data")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileData {
    @Id
    private UUID id;
    private String name;
}
