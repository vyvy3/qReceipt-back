package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.config.exception.NotFoundException;
import xyz.qakashi.qreceipt.domain.User;
import xyz.qakashi.qreceipt.repository.UserRepository;
import xyz.qakashi.qreceipt.service.UserService;
import xyz.qakashi.qreceipt.util.Mapper;
import xyz.qakashi.qreceipt.web.dto.ProfileViewDto;

import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Override
    public void assignProfilePicture(String email, UUID photoUuid) {
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);
        if (isNull(user)) {
            throw NotFoundException.userNotFoundByEmail(email);
        }
        user.setPicture(photoUuid);
        user = userRepository.save(user);
    }

    @Override
    public ProfileViewDto getProfileByEmail(String email) {
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);
        if (isNull(user)) {
            throw NotFoundException.userNotFoundByEmail(email);
        }

        return mapper.toProfileViewDto(user);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }
}
