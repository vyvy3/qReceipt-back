package xyz.qakashi.qreceipt.config.exception;

import org.springframework.http.HttpStatus;

public class ServerException extends BaseException {

    public ServerException(String description) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, description);
    }

    public static ServerException errorDuringAuthentication() {
        return new ServerException("error-during-authentication");
    }

    public static ServerException noReceiptFormIsPresent() {
        return new ServerException("no-receipt-form-is-present");
    }

    public static ServerException errorWhileReadingFile() {
        return new ServerException("error-while-reading-file");
    }

    public static ServerException errorWhileSavingFile() {
        return new ServerException("error-while-saving-file");
    }
}
