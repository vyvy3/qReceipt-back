package xyz.qakashi.qreceipt.service;

import xyz.qakashi.qreceipt.web.dto.user.EditProfileDto;
import xyz.qakashi.qreceipt.web.dto.user.ProfileViewDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    boolean emailExists(String email);

    void assignProfilePicture(String email, UUID photoUuid);

    ProfileViewDto getProfileByEmail(String email);

    void editProfileByEmail(String email, EditProfileDto dto);

    List<String> getGenders();
}
