package com.lpb.esb.service.fileconverter.controller;

import com.lpb.esb.service.fileconverter.model.FileConfig;
import com.lpb.esb.service.fileconverter.service.DocxConverterService;
import com.lpb.esb.service.fileconverter.util.ResourceUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by tudv1 on 2022-06-23
 */
@RestController
@RequestMapping(value = "docx")
public class DocxConverterController {
    @Autowired
    DocxConverterService docxConverterService;

    @Autowired
    ResourceUtils resourceUtils;

    @Autowired
    FileConfig fileConfig;

    @RequestMapping(value = "convert-2-pdf", method = RequestMethod.POST)
    public ResponseEntity<?> convertToPdf(@RequestParam("file") MultipartFile fileInput) {
        String outFileName = FilenameUtils.removeExtension(fileInput.getOriginalFilename()) + ".pdf";
        String linkOutput = docxConverterService.convertToPdf(fileInput, outFileName);
        Resource file = resourceUtils.load(fileConfig.getPdfFileRoot(), outFileName);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
