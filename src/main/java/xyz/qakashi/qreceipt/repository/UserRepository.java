package xyz.qakashi.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qreceipt.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmailIgnoreCase(String email);

    boolean existsById(Long id);

    Optional<User> findByEmailIgnoreCase(String email);
}
