package xyz.qakashi.qrecipient.service;

import xyz.qakashi.qrecipient.domain.enums.FileExtension;

public interface FileService {
    void saveFile (String fileName, FileExtension extension, byte[] bytes);
}
