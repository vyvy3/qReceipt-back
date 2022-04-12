package xyz.qakashi.qrecipient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qrecipient.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
