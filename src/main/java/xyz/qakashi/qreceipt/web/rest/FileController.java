package xyz.qakashi.qreceipt.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.qakashi.qreceipt.service.FileService;

import java.util.UUID;

import static xyz.qakashi.qreceipt.util.Constants.PUBLIC_API_ENDPOINT;

@RestController
@RequestMapping(PUBLIC_API_ENDPOINT + "/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(value = "/save",  consumes = "multipart/form-data")
    public ResponseEntity<UUID> save(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(fileService.save(file));
    }

    @GetMapping(value = "/get/{uuid}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> get(@PathVariable("uuid") UUID id) {

        return fileService.getResponse(id);
    }

    @GetMapping(value = "/preview/{uuid}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable("uuid") UUID id) {
        return fileService.preview(id);
    }
}
