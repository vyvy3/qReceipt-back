package xyz.qakashi.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qreceipt.domain.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
