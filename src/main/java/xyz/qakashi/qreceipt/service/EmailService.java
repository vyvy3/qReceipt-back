package xyz.qakashi.qreceipt.service;

import xyz.qakashi.qreceipt.domain.User;
import xyz.qakashi.qreceipt.web.dto.ResponseDto;

public interface EmailService {
    void sendEmail(User user, String uuid);

    ResponseDto verifyEmail(String uuid, String code);
}
