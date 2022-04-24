package xyz.qakashi.qrecipient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qrecipient.domain.ReceiptForm;

@Repository
public interface ReceiptFormRepository extends JpaRepository<ReceiptForm, Long> {
}
