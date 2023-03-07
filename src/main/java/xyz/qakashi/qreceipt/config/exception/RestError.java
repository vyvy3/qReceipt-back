package xyz.qakashi.qreceipt.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class RestError {
    private ZonedDateTime timestamp;
    private String error;
    private String message;

    public RestError(final BaseException exception) {
        this.timestamp = ZonedDateTime.now();
        this.error = exception.getMessage();
        this.message = exception.getDescription();
    }

    public RestError(RuntimeException exception) {
        this.timestamp = ZonedDateTime.now();
        this.error = exception.getMessage();
    }
}
