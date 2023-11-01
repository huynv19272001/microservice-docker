package com.lpb.esb.service.file.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileTransferService {
    ResponseModel uploadFile(MultipartFile file) throws IOException;

    boolean downloadFile(String fileName) throws IOException;

    FTPFile[] getListFile() throws IOException;

    boolean checkFile(String fileName) throws IOException;
}
