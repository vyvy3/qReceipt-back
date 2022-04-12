package xyz.qakashi.qrecipient.service.impl;


import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.qakashi.qrecipient.config.JwtCoder;
import xyz.qakashi.qrecipient.domain.Role;
import xyz.qakashi.qrecipient.domain.User;
import xyz.qakashi.qrecipient.domain.UserDetailedInfo;
import xyz.qakashi.qrecipient.repository.EmailVerificationRepository;
import xyz.qakashi.qrecipient.repository.RoleRepository;
import xyz.qakashi.qrecipient.repository.UserDetailedInfoRepository;
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

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;
    private final UserDetailedInfoRepository userDetailedInfoRepository;

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
                .roles(Sets.newHashSet(roleRepository.getById(Role.ROLE_USER)))
                .verified(false).build();
        user = userRepository.save(user);
        UserDetailedInfo userDetailedInfo = new UserDetailedInfo(dto);
        userDetailedInfo.setUser(user);
        userDetailedInfoRepository.save(userDetailedInfo);
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
                .verified(true).build();
        user = userRepository.save(user);
        UserDetailedInfo userDetailedInfo = new UserDetailedInfo(dto);
        userDetailedInfo.setUser(user);
        userDetailedInfoRepository.save(userDetailedInfo);
        responseDto.setSuccess(true);
        return responseDto;
    }

    @Override
    public ResponseDto signIn(LoginDto dto) {
        ResponseDto response = new ResponseDto<>();
        if (!userRepository.existsByUsername(dto.getUsername())) {
            response.setStatus(NOT_FOUND.value());
            response.setErrorMessage(ErrorMessage.userNotFoundByUsername(dto.getUsername()));
            return response;
        }
        User user = userRepository.findByUsername(dto.getUsername()).get();
        if (!PasswordEncoder.verifyPassword(dto.getPassword(), user.getPassword()) && user.getVerified().equals(true)) {
            response.setStatus(BAD_REQUEST.value());
            response.setErrorMessage(ErrorMessage.incorrectPassword());
            return response;
        } else {
            response.setSuccess(true);
            response.setData(AuthResponseDto.builder().accessToken(JwtCoder.generateJwt(user)).build());
            return response;
        }
    }


}
