package xyz.qakashi.qreceipt.service;

import xyz.qakashi.qreceipt.domain.enums.FileExtension;

public interface FileService {
    void saveFile (String fileName, FileExtension extension, byte[] bytes);
}
