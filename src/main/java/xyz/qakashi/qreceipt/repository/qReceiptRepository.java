package xyz.qakashi.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qreceipt.domain.qReceipt;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface qReceiptRepository extends JpaRepository<qReceipt, UUID> {
    List<qReceipt> findAllByAuthor (String author);

    @Query("SELECT CAST(MONTH(e.createdDate) AS string) as month, " +
            "CAST(SUM(e.totalSum) AS string) as sum " +
            "FROM qReceipt e " +
            "WHERE e.createdDate >= :startDate " +
            "GROUP BY MONTH(e.createdDate)" +
            "ORDER BY MONTH(e.createdDate) ASC")
    List<Map<String, String>> getTotalSum(@Param("startDate") ZonedDateTime startDate);
}
