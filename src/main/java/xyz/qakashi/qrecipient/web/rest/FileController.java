package xyz.qakashi.qrecipient.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/public/file")
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(FileController.class);
    // Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "\\files\\";
    // Single File upload
    @PostMapping(value = "/upload", consumes = {
            "multipart/form-data"
    })
    @Operation(summary = "Upload a single File")
    public ResponseEntity< ? > uploadFile(@RequestPart("file") MultipartFile uploadfile) {
        logger.debug("Single file upload!");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("You must select a file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadfile));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity < > (HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
    // Multiple File upload
    @PostMapping(value = "/multipleupload", consumes = {
            "multipart/form-data"
    })
    @Operation(summary = "Upload multiple Files")
    public ResponseEntity uploadFiles(@RequestPart String metaData, @RequestPart(required = true) MultipartFile[] uploadfiles) {
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename()).filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadfiles));
        } catch (IOException e) {
            return new ResponseEntity < > (HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadedFileName, HttpStatus.OK);
    }
    // Single File download
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    @Operation(summary = "Download a File")
    public ResponseEntity <Resource> downloadFile(String fileName) throws IOException {
        File file = new File(UPLOADED_FOLDER + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        Path path = Paths.get(UPLOADED_FOLDER + fileName);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    // save file
    private void saveUploadedFiles(List< MultipartFile > files) throws IOException {
        File folder = new File(UPLOADED_FOLDER);
        if (!folder.exists()) {
            logger.info(String.valueOf(Files.createDirectory(folder.toPath())));
        }
        for (MultipartFile file: files) {
            if (file.isEmpty()) {
                continue;
                // next pls
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }
}