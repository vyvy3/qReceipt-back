package xyz.qakashi.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qreceipt.domain.qReceipt;

import java.util.List;
import java.util.UUID;

@Repository
public interface qReceiptRepository extends JpaRepository<qReceipt, UUID> {
    List<qReceipt> findAllByAuthor (String author);
}
