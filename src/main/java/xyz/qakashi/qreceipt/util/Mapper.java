package xyz.qakashi.qreceipt.util;

import org.mapstruct.InjectionStrategy;
import xyz.qakashi.qreceipt.domain.User;
import xyz.qakashi.qreceipt.web.dto.ProfileViewDto;

@org.mapstruct.Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface Mapper {
    ProfileViewDto toProfileViewDto(User user);
}