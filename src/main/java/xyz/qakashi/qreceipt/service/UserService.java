package xyz.qakashi.qreceipt.service;

import xyz.qakashi.qreceipt.web.dto.user.ProfileViewDto;

import java.util.UUID;

public interface UserService {
    boolean emailExists(String email);

    void assignProfilePicture(String email, UUID photoUuid);

    ProfileViewDto getProfileByEmail(String email);
}
