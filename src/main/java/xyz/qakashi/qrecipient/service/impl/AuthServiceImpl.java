package xyz.qakashi.qrecipient.service.impl;


import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.qakashi.qrecipient.config.JwtCoder;
import xyz.qakashi.qrecipient.domain.Role;
import xyz.qakashi.qrecipient.domain.User;
import xyz.qakashi.qrecipient.repository.EmailVerificationRepository;
import xyz.qakashi.qrecipient.repository.RoleRepository;
import xyz.qakashi.qrecipient.repository.UserRepository;
import xyz.qakashi.qrecipient.service.AuthService;
import xyz.qakashi.qrecipient.service.EmailService;
import xyz.qakashi.qrecipient.util.ErrorMessage;
import xyz.qakashi.qrecipient.util.PasswordEncoder;
import xyz.qakashi.qrecipient.web.dto.AuthResponseDto;
import xyz.qakashi.qrecipient.web.dto.LoginDto;
import xyz.qakashi.qrecipient.web.dto.RegisterDto;
import xyz.qakashi.qrecipient.web.dto.ResponseDto;

import java.util.UUID;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;

    @Override
    public ResponseDto emailSignUp(RegisterDto dto) {
        ResponseDto responseDto = new ResponseDto<>();
        if (userRepository.existsByUsername(dto.getUsername())) {
            responseDto.setStatus(BAD_REQUEST.value());
            responseDto.setErrorMessage(ErrorMessage.userWithLoginExists(dto.getUsername()));
            return responseDto;
        }
        dto.setPassword(PasswordEncoder.encode(dto.getPassword()));
        User user = User.builder()
                .password(dto.getPassword())
                .username(dto.getUsername())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .gender(dto.getGender())
                .roles(Sets.newHashSet(roleRepository.getById(Role.ROLE_USER)))
                .verified(false)
                .build();
        user = userRepository.save(user);
        String uuid = UUID.randomUUID().toString();
        emailService.sendEmail(user, uuid);
        responseDto.setSuccess(true);
        responseDto.setData(uuid);
        return responseDto;
    }

    @Override
    public ResponseDto simpleSignUp(RegisterDto dto) {
        ResponseDto responseDto = new ResponseDto<>();
        if (userRepository.existsByUsername(dto.getUsername())) {
            responseDto.setStatus(BAD_REQUEST.value());
            responseDto.setErrorMessage(ErrorMessage.userWithLoginExists(dto.getUsername()));
            return responseDto;
        }
        dto.setPassword(PasswordEncoder.encode(dto.getPassword()));
        User user = User.builder()
                .password(dto.getPassword())
                .username(dto.getUsername())
                .roles(Sets.newHashSet(roleRepository.getById(Role.ROLE_USER)))
                .verified(true)
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .gender(dto.getGender())
                .build();
        user = userRepository.save(user);
        responseDto.setSuccess(true);
        return responseDto;
    }

    @Override
    public ResponseDto signIn(LoginDto dto) {
        ResponseDto response = new ResponseDto<>();

        User user = userRepository.findByUsername(dto.getUsername()).orElse(null);

        if (isNull(user)) {
            response.setStatus(NOT_FOUND.value());
            response.setErrorMessage(ErrorMessage.userNotFoundByUsername(dto.getUsername()));
            return response;
        }

        if (!PasswordEncoder.verifyPassword(dto.getPassword(), user.getPassword()) && user.getVerified().equals(true)) {
            response.setStatus(BAD_REQUEST.value());
            response.setErrorMessage(ErrorMessage.incorrectPassword());
            return response;
        }

        if (isNull(user.getVerified()) || !user.getVerified()) {
            response.setStatus(BAD_REQUEST.value());
            response.setErrorMessage(ErrorMessage.userNotVerified());
            return response;
        }

        response.setSuccess(true);
        response.setData(AuthResponseDto.builder().accessToken(JwtCoder.generateJwt(user)).build());
        return response;

    }


}
