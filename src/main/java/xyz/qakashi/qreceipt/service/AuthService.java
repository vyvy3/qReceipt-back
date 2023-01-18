package xyz.qakashi.qreceipt.service;

import xyz.qakashi.qreceipt.web.dto.LoginDto;
import xyz.qakashi.qreceipt.web.dto.RegisterDto;
import xyz.qakashi.qreceipt.web.dto.ResponseDto;

public interface AuthService {
    ResponseDto emailSignUp(RegisterDto dto);

    ResponseDto simpleSignUp (RegisterDto dto);

    ResponseDto signIn(LoginDto dto);
}
