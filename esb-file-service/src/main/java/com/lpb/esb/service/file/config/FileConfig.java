package com.lpb.esb.service.file.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@Getter
@Setter
public class FileConfig {
    @Value("${file-config.HOST_SFTP}")
    private String hostSFTP;
    @Value("${file-config.PORT_SFTP}")
    private Integer portSFTP;
    @Value("${file-config.USER_SFTP}")
    private String userSFTP;
    @Value("${file-config.PASSWORD_SFTP}")
    private String passwordSFTP;
    @Value("${file-config.DATA_TIMEOUT}")
    private Integer dataTimeOut;
    @Value("${file-config.CONNECT_TIMEOUT}")
    private Integer connectTimeOut;
    @Value("${file-config.REMOTE_FILE_PATH_DOWNLOAD}")
    private String remoteFilePathDownload;
    @Value("${file-config.REMOTE_FILE_PATH_UPLOAD}")
    private String remoteFilePathUpload;
    @Value("${file-config.REMOTE_FILE_PATH_SFTP}")
    private String remoteFilePathSFTP;
}
