package xyz.qakashi.qreceipt.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
