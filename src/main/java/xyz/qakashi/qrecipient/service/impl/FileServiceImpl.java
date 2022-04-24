package xyz.qakashi.qrecipient.service.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import xyz.qakashi.qrecipient.domain.enums.FileExtension;
import xyz.qakashi.qrecipient.service.FileService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    private static final String UPLOADED_FOLDER = "\\files\\";

    @Override
    @SneakyThrows
    public void saveFile(String fileName, FileExtension extension, byte[] bytes) {
        Path path = Paths.get(UPLOADED_FOLDER + fileName + "." + extension.name().toLowerCase());
        Files.write(path, bytes);
    }
}
