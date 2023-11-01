package com.lpb.esb.service.fileconverter.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by tudv1 on 2022-06-23
 */
public interface FIleConverterService {
    String convertToPdf(MultipartFile file, String outputName);
}
