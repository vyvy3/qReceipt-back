package xyz.qakashi.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qreceipt.domain.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, String> {
}
