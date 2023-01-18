package xyz.qakashi.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qreceipt.domain.qReceipt;

import java.util.List;

@Repository
public interface qReceiptRepository extends JpaRepository<qReceipt, Long> {
    List<qReceipt> findAllByAuthor (String author);
}
