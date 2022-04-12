package xyz.qakashi.qrecipient.service;

import xyz.qakashi.qrecipient.domain.User;
import xyz.qakashi.qrecipient.web.dto.ResponseDto;

public interface EmailService {
    void sendEmail(User user, String uuid);

    ResponseDto verifyEmail(String uuid, String code);
}
