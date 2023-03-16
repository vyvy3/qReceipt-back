package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.qakashi.qreceipt.config.exception.NotFoundException;
import xyz.qakashi.qreceipt.config.exception.ServerException;
import xyz.qakashi.qreceipt.domain.FileData;
import xyz.qakashi.qreceipt.repository.FileRepository;
import xyz.qakashi.qreceipt.service.FileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.util.Objects.isNull;
import static xyz.qakashi.qreceipt.util.Constants.FILE_UPLOAD_FOLDER;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public Resource get(UUID id) {
        FileData fileData = fileRepository.findById(id).orElse(null);
        if (isNull(fileData)) {
            throw NotFoundException.fileNotFound();
        }

        Path path = Paths.get(FILE_UPLOAD_FOLDER + fileData.getName());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw ServerException.errorWhileReadingFile();
        }
        return resource;
    }

    @Override
    public ResponseEntity<Resource> getResponse(UUID id) {
        FileData fileData = fileRepository.findById(id).orElse(null);
        if (isNull(fileData)) {
            throw NotFoundException.fileNotFound();
        }

        Path path = Paths.get(FILE_UPLOAD_FOLDER + fileData.getName());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw ServerException.errorWhileReadingFile();
        }
        File fileInfo = new File(FILE_UPLOAD_FOLDER + fileData.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileData.getName());
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileInfo.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Override
    public boolean existsById(UUID id) {
        return fileRepository.existsById(id);
    }

    @Override
    public UUID save(String fileName, byte[] file) {
        UUID id = UUID.randomUUID();    // generate random UUID to use it as file reference
        FileData fileData = new FileData();
        fileData.setId(id);
        fileData.setName(fileName);
        fileRepository.save(fileData);

        File localFileData = new File(FILE_UPLOAD_FOLDER + fileName);

        // Write the file to the local file system
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(localFileData);
            outputStream.write(file);
            outputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw ServerException.errorWhileSavingFile();
        }

        return id;
    }

    @Override
    public ResponseEntity<byte[]> preview(UUID id) {
        FileData fileData = fileRepository.findById(id).orElse(null);
        if (isNull(fileData)) {
            throw NotFoundException.fileNotFound();
        }
        File file = new File(FILE_UPLOAD_FOLDER + fileData.getName());
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        if (fileData.getName().toLowerCase().endsWith(".jpg") || fileData.getName().toLowerCase().endsWith(".jpeg")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (fileData.getName().toLowerCase().endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (fileData.getName().toLowerCase().endsWith(".gif")) {
            mediaType = MediaType.IMAGE_GIF;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        // Return the file contents as a response entity
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(fileContent);
    }

    @Override
    public UUID save(MultipartFile saveFile) {
        UUID id = UUID.randomUUID();    // generate random UUID to use it as file reference
        FileData fileData = new FileData();
        fileData.setId(id);
        fileData.setName(saveFile.getOriginalFilename());
        fileRepository.save(fileData);

        File localFileData = new File(FILE_UPLOAD_FOLDER + saveFile.getOriginalFilename());

        // Write the file to the local file system
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(localFileData);
            outputStream.write(saveFile.getBytes());
            outputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw ServerException.errorWhileSavingFile();
        }

        return id;
    }
}