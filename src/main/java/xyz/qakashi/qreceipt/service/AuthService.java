package xyz.qakashi.qreceipt.service;


import xyz.qakashi.qreceipt.domain.enums.VerificationType;
import xyz.qakashi.qreceipt.web.dto.user.AuthResponseDto;
import xyz.qakashi.qreceipt.web.dto.user.LoginDto;
import xyz.qakashi.qreceipt.web.dto.user.RegistrationDto;

public interface AuthService {
    void registration(RegistrationDto dto);

    void sendRegistrationCode(String email);

    AuthResponseDto login(LoginDto dto);

    void compareSendCodeByType(String email, String code, VerificationType type);

    void sendPasswordRecoveryCode(String email);

    void updatePassword(String email, String password);
}
