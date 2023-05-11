package xyz.qakashi.qreceipt.web.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.qakashi.qreceipt.domain.User;
import xyz.qakashi.qreceipt.domain.enums.MerchandiseCategory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {
    private String name;
    private String description;
    private UUID picture;
    private MerchandiseCategory merchandiseCategory;
}
