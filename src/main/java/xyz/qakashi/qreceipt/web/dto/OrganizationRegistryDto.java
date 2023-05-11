package xyz.qakashi.qreceipt.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.qakashi.qreceipt.domain.Organization;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRegistryDto {
    private String name;
    private String category;
    private UUID picture;

    public OrganizationRegistryDto(Organization organization) {
        this.name = organization.getName();
        this.category = organization.getMerchandiseCategory().name();
        this.picture = organization.getPicture();
    }
}
