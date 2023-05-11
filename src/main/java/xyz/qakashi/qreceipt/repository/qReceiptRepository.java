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

    List<qReceipt> findAllByOwner_EmailIgnoreCase(String email);

    @Query("SELECT CAST(MONTH(e.createdDate) AS string) as month, " +
            "CAST(SUM(e.totalSum) AS string) as sum " +
            "FROM qReceipt e " +
            "WHERE e.createdDate >= :startDate " +
            "GROUP BY MONTH(e.createdDate)" +
            "ORDER BY MONTH(e.createdDate) ASC")
    List<Map<String, String>> getTotalSum(@Param("startDate") ZonedDateTime startDate);

    @Query("SELECT CAST(MONTH(e.createdDate) AS string) as month, " +
            "CAST(SUM(e.totalSum) AS string) as sum " +
            "FROM qReceipt e " +
            "WHERE e.createdDate >= :startDate AND e.owner.email = :email " +
            "GROUP BY MONTH(e.createdDate)" +
            "ORDER BY MONTH(e.createdDate) ASC")
    List<Map<String, String>> getTotalSumByLogin(@Param("startDate") ZonedDateTime startDate,
                                                 @Param("email") String email);

}
