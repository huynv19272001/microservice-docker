package com.lpb.esb.service.file.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lpb.esb.service.file.config.FileConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
public class FilesStorageServiceImpl implements FilesStorageService {
    @Autowired
    FileConfig fileConfig;

    @Override
    public void init() {
        Path root = Paths.get(fileConfig.getRemoteFilePathUpload());
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        Path root = Paths.get(fileConfig.getRemoteFilePathUpload());
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
            if (Files.exists(Paths.get(root + "/" + file.getOriginalFilename()))) {
                File fileExist = new File(root + "/" + file.getOriginalFilename());
                log.info("file exists: " + root + "/" + file.getOriginalFilename());
                fileExist.delete();
                log.info("delete file : " + root + "/" + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
            log.info("upload file susscess: " + root + "/" + file.getOriginalFilename());
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        Path root = Paths.get(fileConfig.getRemoteFilePathUpload());
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        Path root = Paths.get(fileConfig.getRemoteFilePathUpload());
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public File[] loadAll() {
        File folder = new File(fileConfig.getRemoteFilePathUpload());
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }

}
