package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.config.exception.NotFoundException;
import xyz.qakashi.qreceipt.domain.Gender;
import xyz.qakashi.qreceipt.domain.User;
import xyz.qakashi.qreceipt.repository.GenderRepository;
import xyz.qakashi.qreceipt.repository.UserRepository;
import xyz.qakashi.qreceipt.service.UserService;
import xyz.qakashi.qreceipt.util.Mapper;
import xyz.qakashi.qreceipt.web.dto.user.EditProfileDto;
import xyz.qakashi.qreceipt.web.dto.user.ProfileViewDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GenderRepository genderRepository;
    private final Mapper mapper;

    @Override
    public void assignProfilePicture(String email, UUID photoUuid) {
        User user = userRepository.findByLoginIgnoreCase(email).orElse(null);
        if (isNull(user)) {
            throw NotFoundException.userNotFoundByEmail(email);
        }
        user.setPicture(photoUuid);
        user = userRepository.save(user);
    }

    @Override
    public ProfileViewDto getProfileByEmail(String email) {
        User user = userRepository.findByLoginIgnoreCase(email).orElse(null);
        if (isNull(user)) {
            throw NotFoundException.userNotFoundByEmail(email);
        }

        return mapper.toProfileViewDto(user);
    }

    @Override
    public void editProfileByEmail(String email, EditProfileDto dto) {
        User user = userRepository.findByLoginIgnoreCase(email).orElse(null);
        if (isNull(user)) {
            throw NotFoundException.userNotFoundByEmail(email);
        }

        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setGenderSlug(dto.getGender());
        user.setDateOfBirth(dto.getDateOfBirth());
        userRepository.save(user);
    }

    @Override
    public List<String> getGenders() {
        return genderRepository.findAll().stream().map(Gender::getSlug).collect(Collectors.toList());
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByLoginIgnoreCase(email);
    }
}
