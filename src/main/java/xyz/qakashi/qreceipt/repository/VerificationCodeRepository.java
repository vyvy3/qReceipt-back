package xyz.qakashi.qreceipt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qreceipt.domain.VerificationCode;
import xyz.qakashi.qreceipt.domain.enums.VerificationType;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByUser_LoginIgnoreCaseAndVerificationType  (String email, VerificationType verificationType);

}
