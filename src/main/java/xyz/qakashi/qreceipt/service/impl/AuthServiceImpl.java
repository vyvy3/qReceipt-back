package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.config.JwtCoder;
import xyz.qakashi.qreceipt.config.exception.BadRequestException;
import xyz.qakashi.qreceipt.config.exception.NotFoundException;
import xyz.qakashi.qreceipt.domain.Role;
import xyz.qakashi.qreceipt.domain.User;
import xyz.qakashi.qreceipt.domain.VerificationCode;
import xyz.qakashi.qreceipt.domain.enums.VerificationType;
import xyz.qakashi.qreceipt.repository.RoleRepository;
import xyz.qakashi.qreceipt.repository.UserRepository;
import xyz.qakashi.qreceipt.repository.VerificationCodeRepository;
import xyz.qakashi.qreceipt.service.AuthService;
import xyz.qakashi.qreceipt.service.CodeGenerator;
import xyz.qakashi.qreceipt.service.EmailService;
import xyz.qakashi.qreceipt.service.UserService;
import xyz.qakashi.qreceipt.util.PasswordEncoder;
import xyz.qakashi.qreceipt.web.dto.AuthResponseDto;
import xyz.qakashi.qreceipt.web.dto.LoginDto;
import xyz.qakashi.qreceipt.web.dto.RegistrationDto;

import java.time.ZonedDateTime;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static xyz.qakashi.qreceipt.util.Constants.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final UserService userService;
    private final EmailService emailService;

    @Override
    public void registration(RegistrationDto dto) {

        // Essential check that email is free

        if (userService.emailExists(dto.getEmail())) {
            throw BadRequestException.emailIsTaken(dto.getEmail());
        }


        // Main entity filling

        User user = new User();
        Role role = roleRepository.getById(Role.USER);

        user.getRoles().add(role);
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        String encryptedPassword = PasswordEncoder.encode(dto.getPassword());
        user.setPassword(encryptedPassword);

        user = userRepository.save(user);
    }


    @Override
    public void sendRegistrationCode(String email) {
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);
        if (isNull(user)) {
            throw NotFoundException.userNotFoundByEmail(email);
        }

        VerificationCode verificationCode = verificationCodeRepository.findByUser_EmailIgnoreCaseAndVerificationType(email, VerificationType.REGISTRATION).orElse(new VerificationCode());
        if (nonNull(verificationCode.getBlockedUntil()) && ZonedDateTime.now().isBefore(verificationCode.getBlockedUntil())) {
            throw BadRequestException.userIsBlocked();
        }
        verificationCode.setVerificationType(VerificationType.REGISTRATION);
        verificationCode.setNumberOfTries(0);
        String generatedCode = CodeGenerator.byLength(CODE_DEFAULT_LENGTH);
        emailService.sendSimpleMessage(email, "Registration code", generatedCode);
        verificationCode.setCode(generatedCode);
        verificationCode.setSendTime(ZonedDateTime.now());
        verificationCode.setBlockedUntil(null);
        verificationCode.setUserId(user.getId());
        verificationCode.setConfirmed(false);

        verificationCodeRepository.save(verificationCode);
    }

    @Override
    public AuthResponseDto login(LoginDto dto) {
        User user = userRepository.findByEmailIgnoreCase(dto.getEmail()).orElse(null);

        if (isNull(user)) {
            throw NotFoundException.userNotFoundByEmail(dto.getEmail());
        }

        if (!user.isVerified()) {
            throw BadRequestException.userNotVerified();
        }

        if (!PasswordEncoder.verifyPassword(dto.getPassword(), user.getPassword())) {
            throw BadRequestException.incorrectPassword();
        }

        AuthResponseDto response = new AuthResponseDto(JwtCoder.generateJwt(user));
        return response;
    }

    @Override
    public void sendPasswordRecoveryCode(String email) {
        User user;
        user = userRepository.findByEmailIgnoreCase(email).orElse(null);

        if (isNull(user)) {
            throw NotFoundException.userNotFoundByEmail(email);
        }

        VerificationCode verificationCode;
        verificationCode = verificationCodeRepository.findByUser_EmailIgnoreCaseAndVerificationType(email, VerificationType.PASSWORD_RECOVERY).orElse(new VerificationCode());

        if (nonNull(verificationCode.getBlockedUntil()) && ZonedDateTime.now().isBefore(verificationCode.getBlockedUntil())) {
            throw BadRequestException.userIsBlocked();
        }
        verificationCode.setVerificationType(VerificationType.PASSWORD_RECOVERY);
        verificationCode.setNumberOfTries((short) 0);
        verificationCode.setSendTime(ZonedDateTime.now());
        verificationCode.setBlockedUntil(null);
        verificationCode.setUserId(user.getId());
        verificationCode.setConfirmed(false);

        String generatedCode = CodeGenerator.byLength(CODE_DEFAULT_LENGTH);
        emailService.sendSimpleMessage(email, "Password recovery code", generatedCode);

        verificationCode.setCode(generatedCode);
        verificationCodeRepository.save(verificationCode);
    }

    @Override
    public void compareSendCodeByType(String email, String code, VerificationType type) {
        VerificationCode verificationCode;
        verificationCode = verificationCodeRepository.findByUser_EmailIgnoreCaseAndVerificationType(email, type).orElse(null);


        if (isNull(verificationCode)) {
            throw BadRequestException.incorrectCode();
        }

        if (verificationCode.isConfirmed()) {
            throw BadRequestException.codeAlreadyConfirmed();
        }

        User user = verificationCode.getUser();
        Integer count = verificationCode.getNumberOfTries();
        String sentCode = verificationCode.getCode();
        ZonedDateTime codeActiveTime = verificationCode.getExpirationTime();

        if (count < CODE_MAX_ATTEMPT_COUNT) {
            if (Objects.equals(sentCode, code)) {
                if (ZonedDateTime.now().isBefore(codeActiveTime)) {
                    if (type.equals(VerificationType.REGISTRATION)) {
                        user.setVerified(true);
                        userRepository.save(user);
                    }
                    verificationCode.setConfirmed(true);
                    verificationCodeRepository.save(verificationCode);
                } else {
                    throw BadRequestException.codeExpired();
                }
            } else {
                count++;
                verificationCode.setNumberOfTries(count);
                verificationCodeRepository.save(verificationCode);
                throw BadRequestException.codeMismatch();
            }
        } else {
            if (isNull(verificationCode.getBlockedUntil())) {
                verificationCode.setBlockedUntil(ZonedDateTime.now().plusMinutes(CODE_BLOCK_TIME_IN_MINUTES));
                verificationCodeRepository.save(verificationCode);
            }
            throw BadRequestException.codeExcessCount();
        }
    }

    @Override
    public void updatePassword(String email, String password) {
        VerificationCode verificationCode;

        verificationCode = verificationCodeRepository.findByUser_EmailIgnoreCaseAndVerificationType(email, VerificationType.PASSWORD_RECOVERY).orElse(null);

        if (isNull(verificationCode) || !verificationCode.isConfirmed()) {
            throw BadRequestException.codeNotConfirmed();
        }
        User user = verificationCode.getUser();
        user.setPassword(PasswordEncoder.encode(password));
        verificationCode.clearCode();
        verificationCode.setConfirmed(false);
        userRepository.save(user);
        verificationCodeRepository.save(verificationCode);
    }
}
