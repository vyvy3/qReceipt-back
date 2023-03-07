package xyz.qakashi.qreceipt.config.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

    public BadRequestException(String description) {
        super(HttpStatus.BAD_REQUEST, description);
    }

    public static BadRequestException requiredParameterIsEmpty(String parameter) {
        return new BadRequestException(String.format("parameter-empty-%s", parameter));
    }

    public static BadRequestException emailIsTaken(String email) {
        return new BadRequestException(String.format("user-with-email-%s-already-exists", email));
    }

    public static BadRequestException incorrectPassword() {
        return new BadRequestException("password-is-incorrect");
    }

    public static BadRequestException userIsBlocked() {
        return new BadRequestException("user-is-blocked-try-later");
    }

    public static BadRequestException incorrectCode() {
        return new BadRequestException("code-is-incorrect");
    }

    public static BadRequestException codeAlreadyConfirmed() {
        return new BadRequestException("code-is-already-confirmed");
    }

    public static BadRequestException codeNotConfirmed() {
        return new BadRequestException("code-not-confirmed");
    }

    public static BadRequestException codeExpired() {
        return new BadRequestException("code-is-expired");
    }

    public static BadRequestException codeMismatch() {
        return new BadRequestException("code-mismatch");
    }

    public static BadRequestException codeExcessCount() {
        return new BadRequestException("exceeded-number-of-tries-for-this-code");
    }

    public static BadRequestException userNotVerified() {
        return new BadRequestException("user-is-not-verified");
    }

}
