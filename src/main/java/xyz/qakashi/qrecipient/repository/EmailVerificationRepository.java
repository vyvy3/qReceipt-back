package xyz.qakashi.qrecipient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qrecipient.domain.EmailVerification;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findEmailVerificationByUserId(Long userId);

    Optional<EmailVerification> findByCodeAndVerificationKey(String code, String verificationKey);
}
