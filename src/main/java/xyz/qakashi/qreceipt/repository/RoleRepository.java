package xyz.qakashi.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qreceipt.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
