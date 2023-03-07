package xyz.qakashi.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.qakashi.qreceipt.domain.FileData;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileData, UUID> {
}
