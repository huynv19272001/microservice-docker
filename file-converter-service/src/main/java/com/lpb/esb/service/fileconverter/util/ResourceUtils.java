package com.lpb.esb.service.fileconverter.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by tudv1 on 2022-06-23
 */
@Component
@Log4j2
public class ResourceUtils {

    public Resource load(String rootDir, String filename) {
        Path root = Paths.get(rootDir);
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

    public void checkFolderExistAndCreate(String inputDir) {
        try {
            File dir = new File(inputDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } catch (Exception e) {
            log.error("Exeception: {}", e.getMessage(), e);
        }
    }
}
