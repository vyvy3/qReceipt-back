package xyz.qakashi.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qreceipt.domain.ReceiptForm;

@Repository
public interface ReceiptFormRepository extends JpaRepository<ReceiptForm, Long> {
}
