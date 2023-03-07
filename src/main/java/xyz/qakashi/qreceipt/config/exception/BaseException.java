package xyz.qakashi.qreceipt.config.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@Getter
@Setter
public class BaseException extends RuntimeException {
    private final Integer status;
    private final String description;

    public BaseException(HttpStatus httpStatus, String description) {
        super(httpStatus.getReasonPhrase());
        this.status = httpStatus.value();
        this.description = description;
    }
}
