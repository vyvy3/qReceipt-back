package xyz.qakashi.qreceipt.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import xyz.qakashi.qreceipt.config.exception.BaseException;
import xyz.qakashi.qreceipt.config.exception.RestError;

import static java.util.Objects.isNull;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final int DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR.value();

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<Object> handleBaseException(final BaseException exception) {
        return ResponseEntity.status(isNull(exception.getStatus()) ? DEFAULT_STATUS : exception.getStatus()).body(new RestError(exception));
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(final RuntimeException exception) {
        return ResponseEntity.status(DEFAULT_STATUS).body(new RestError(exception));
    }

    @ExceptionHandler({HttpClientErrorException.class, HttpStatusCodeException.class, HttpServerErrorException.class})
    public ResponseEntity<Object> handleHttpException(HttpStatusCodeException e) {
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(e.getRawStatusCode()).header("X-Backend-Status", String.valueOf(e.getRawStatusCode()));
        if (e.getResponseHeaders().getContentType() != null) {
            bodyBuilder.contentType(e.getResponseHeaders().getContentType());
        }
        return bodyBuilder.body(e.getResponseBodyAsString());
    }
}
