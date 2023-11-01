package com.lpb.esb.service.fileconverter.service;

import com.lpb.esb.service.fileconverter.model.FileConfig;
import com.lpb.esb.service.fileconverter.util.ResourceUtils;
import com.spire.doc.FileFormat;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by tudv1 on 2022-06-23
 */
@Service
@Log4j2
public class DocxConverterService implements FIleConverterService {
    @Autowired
    FileConfig fileConfig;
    @Autowired
    ResourceUtils resourceUtils;

    @Override
    public String convertToPdf(MultipartFile file, String outputName) {
        String outputNameDir = fileConfig.getPdfFileRoot() + outputName;
        try {
            resourceUtils.checkFolderExistAndCreate(fileConfig.getPdfFileRoot());
            //Create a Document instance and load a Word document
            com.spire.doc.Document doc1 = new com.spire.doc.Document(file.getInputStream());

            //Save the document to PDF
            doc1.saveToFile(outputNameDir, FileFormat.PDF);
            return outputNameDir;
        } catch (Throwable e) {
            log.error("error when convert to pdf: {}", e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }
}
