package xyz.qakashi.qreceipt.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {
    Resource get(UUID id);

    ResponseEntity<Resource> getResponse(UUID id);

    boolean existsById(UUID id);

    ResponseEntity<byte[]> preview(UUID id);

    UUID save(MultipartFile saveFile);

    UUID save(String fileName, byte[] file);
}
