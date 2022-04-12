package xyz.qakashi.qrecipient.service;

import xyz.qakashi.qrecipient.web.dto.LoginDto;
import xyz.qakashi.qrecipient.web.dto.RegisterDto;
import xyz.qakashi.qrecipient.web.dto.ResponseDto;

public interface AuthService {
    ResponseDto emailSignUp(RegisterDto dto);

    ResponseDto simpleSignUp (RegisterDto dto);

    ResponseDto signIn(LoginDto dto);
}
