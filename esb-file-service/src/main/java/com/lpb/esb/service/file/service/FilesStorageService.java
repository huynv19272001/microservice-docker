package com.lpb.esb.service.file.service;

import java.io.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    void init();

    void save(MultipartFile file);

    Resource load(String filename);

    void deleteAll();

    File[] loadAll();
}
