package xyz.qakashi.qrecipient.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import xyz.qakashi.qrecipient.domain.EmailVerification;
import xyz.qakashi.qrecipient.domain.User;
import xyz.qakashi.qrecipient.repository.EmailVerificationRepository;
import xyz.qakashi.qrecipient.repository.UserRepository;
import xyz.qakashi.qrecipient.service.EmailService;
import xyz.qakashi.qrecipient.web.dto.ResponseDto;

import java.util.Optional;
import java.util.Random;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final EmailVerificationRepository emailVerificationRepository;
    private final UserRepository userRepository;

    @Value("${email.address}")
    private String EMAIL_ADDRESS;

    @Override
    public void sendEmail(User user, String uuid) {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setVerificationKey(uuid);
        emailVerification.setUserId(user.getId());
        emailVerification.setCode(getRandomCode());
        emailVerificationRepository.save(emailVerification);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Verify email address - ");
        simpleMailMessage.setFrom(EMAIL_ADDRESS);
        simpleMailMessage.setText("Your code to verify your email address " + emailVerification.getCode());
        simpleMailMessage.setTo(user.getUsername());
        javaMailSender.send(simpleMailMessage);
        log.info("code have sent to email address - {}", user.getUsername());
    }

    private String getRandomCode() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%6d", number);
    }

    @Override
    public ResponseDto verifyEmail(String uuid, String code) {
        ResponseDto responseDto = new ResponseDto<>();
        Optional<EmailVerification> emailVerification = emailVerificationRepository
                .findByCodeAndVerificationKey(code, uuid);
        if (emailVerification.isPresent()) {
            User userObj = emailVerification.get().getUser();
            userObj.setVerified(true);
            userRepository.save(userObj);
            emailVerificationRepository.delete(emailVerification.get());
            responseDto.setSuccess(true);
            return responseDto;
        }
        responseDto.setSuccess(false);
        responseDto.setStatus(NOT_FOUND.value());
        responseDto.setErrorMessage("Not found");
        return responseDto;
    }
}
