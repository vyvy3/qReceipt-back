package xyz.qakashi.qreceipt.util;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import xyz.qakashi.qreceipt.domain.Organization;
import xyz.qakashi.qreceipt.domain.User;
import xyz.qakashi.qreceipt.web.dto.organization.OrganizationDto;
import xyz.qakashi.qreceipt.web.dto.organization.OrganizationViewDto;
import xyz.qakashi.qreceipt.web.dto.user.ProfileViewDto;

@org.mapstruct.Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface Mapper {
    @Mappings({
            @Mapping(target = "email", source = "login"),
            @Mapping(target = "gender", source = "genderSlug")
    })
    ProfileViewDto toProfileViewDto(User user);

    Organization toOrganization(OrganizationDto dto);

    OrganizationViewDto toDto(Organization organization);
}