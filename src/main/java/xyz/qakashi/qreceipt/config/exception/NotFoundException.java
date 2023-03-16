package xyz.qakashi.qreceipt.config.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    public NotFoundException(String description) {
        super(HttpStatus.NOT_FOUND, description);
    }

    public static NotFoundException entityNotFound(String entityName) {
        return new NotFoundException(String.format("%s-not-found", entityName));
    }

    public static NotFoundException entityNotFoundById(String entityName, String id) {
        return new NotFoundException(String.format("%s-not-found-by-id-%s", entityName, id));
    }

    public static NotFoundException entityNotFoundById(String entityName, Long id) {
        return new NotFoundException(String.format("%s-not-found-by-id-%s", entityName, id));
    }

    public static NotFoundException userNotFoundByEmail(String email) {
        return new NotFoundException(String.format("user-not-found-by-email-%s", email));
    }

    public static NotFoundException fileNotFound() {
        return new NotFoundException("file-is-not-found");
    }
}