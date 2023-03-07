package xyz.qakashi.qreceipt.config.exception;

import org.springframework.http.HttpStatus;

public class ServerException extends BaseException {

    public ServerException(String description) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, description);
    }

    public static NotFoundException errorDuringAuthentication() {
        return new NotFoundException("error-during-authentication");
    }
}
