package xyz.qakashi.qrecipient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qrecipient.domain.UserDetailedInfo;

@Repository
public interface UserDetailedInfoRepository extends JpaRepository<UserDetailedInfo, Long> {
}
