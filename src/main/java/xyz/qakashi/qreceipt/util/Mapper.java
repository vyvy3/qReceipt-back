package xyz.qakashi.qreceipt.util;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import xyz.qakashi.qreceipt.domain.User;
import xyz.qakashi.qreceipt.web.dto.ProfileViewDto;

@org.mapstruct.Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface Mapper {
    @Mappings({@Mapping(target = "email", source = "login")})
    ProfileViewDto toProfileViewDto(User user);
}