package xyz.qakashi.qreceipt.web.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.qakashi.qreceipt.domain.enums.VerificationType;
import xyz.qakashi.qreceipt.service.AuthService;
import xyz.qakashi.qreceipt.service.UserService;
import xyz.qakashi.qreceipt.web.dto.AuthResponseDto;
import xyz.qakashi.qreceipt.web.dto.BaseResponseDto;
import xyz.qakashi.qreceipt.web.dto.LoginDto;
import xyz.qakashi.qreceipt.web.dto.RegistrationDto;

import static xyz.qakashi.qreceipt.util.Constants.PUBLIC_API_ENDPOINT;


@RestController
@RequestMapping(PUBLIC_API_ENDPOINT + "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/emailExists/{email}")
    @ApiOperation("Check if the email is already in the database")
    public ResponseEntity<BaseResponseDto> emailExists(
            @ApiParam("Email") @PathVariable(name = "email") String email
    ) {
        return ResponseEntity.ok(new BaseResponseDto(String.valueOf(userService.emailExists(email))));
    }

    @PostMapping("/registration/createUser")
    @ApiOperation("Registration of a new user, creating field in the database")
    public ResponseEntity<AuthResponseDto> registrationCreateUser(
            @ApiParam("Registration dto") @RequestBody RegistrationDto dto
    ) {
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registration/sendEmailCode/{email}")
    @ApiOperation("Registration of a new user, sending verification code to the email")
    public ResponseEntity<AuthResponseDto> registrationSendCode(
            @ApiParam("User email") @PathVariable("email") String email
    ) {
        authService.sendRegistrationCode(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registration/confirmEmail/{email}/{code}")
    @ApiOperation("Email confirmation")
    public ResponseEntity registrationConfirmEmail(
            @ApiParam(value = "Email") @PathVariable(name = "email") String email,
            @ApiParam(value = "Code") @PathVariable(name = "code") String code
    ) {
        authService.compareSendCodeByType(email, code, VerificationType.REGISTRATION);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @ApiOperation("Login")
    public ResponseEntity<AuthResponseDto> login(
            @ApiParam("Login dto") @RequestBody LoginDto dto
    ) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/passwordRecovery/sendCode/{email}")
    @ApiOperation("Send email with password recovery code")
    public ResponseEntity sendPasswordRecoveryMail(
            @ApiParam(value = "Email") @PathVariable(name = "email") String email
    ) {
        authService.sendPasswordRecoveryCode(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/passwordRecovery/confirmCode/{email}/{code}")
    @ApiOperation("Password recovery code confirmation")
    public ResponseEntity passwordRecoveryConfirmCode(
            @ApiParam(value = "Email") @PathVariable(name = "email") String email,
            @ApiParam(value = "Code") @PathVariable(name = "code") String code
    ) {
        authService.compareSendCodeByType(email, code, VerificationType.PASSWORD_RECOVERY);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/passwordRecovery/complete/{email}/{password}")
    @ApiOperation("Changing password after code confirmation")
    public ResponseEntity completePasswordRecovery(@ApiParam(value = "Email") @PathVariable("email") String email,
                                                   @ApiParam(value = "Password") @PathVariable("password") String password) {
        authService.updatePassword(email, password);
        return ResponseEntity.ok().build();
    }

}
