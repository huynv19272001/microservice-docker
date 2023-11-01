package com.lpb.esb.service.fileconverter.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tudv1 on 2022-02-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Configuration
public class FileConfig {
    @Value("${file.root.pdf}")
    String pdfFileRoot;

}
