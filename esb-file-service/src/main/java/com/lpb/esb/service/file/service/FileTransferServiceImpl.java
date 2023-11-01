package com.lpb.esb.service.file.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.file.config.FileConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
public class FileTransferServiceImpl implements FileTransferService {
    @Autowired
    FileConfig fileConfig;

    @Override
    public ResponseModel uploadFile(MultipartFile file) throws IOException {
        System.setProperty("jdk.tls.useExtendedMasterSecret", "false");
        FTPSClient ftpClient = new FTPSClient("TLS", false);
        ftpClient.addProtocolCommandListener(
            new PrintCommandListener(
                new PrintWriter(new OutputStreamWriter(System.out, "UTF-8")), true));
        BufferedInputStream buffIn = null;

        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            ftpClient.connect(fileConfig.getHostSFTP(), fileConfig.getPortSFTP());
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.error("FTP server refused connection.");
            }
            ftpClient.setBufferSize(10000);
            ftpClient.enterLocalPassiveMode();
            ftpClient.login(fileConfig.getUserSFTP(), fileConfig.getPasswordSFTP());
            ftpClient.execPBSZ(0);
            ftpClient.execPROT("P");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(fileConfig.getRemoteFilePathSFTP());
            log.info("upload file " + fileConfig.getRemoteFilePathSFTP() + file.getOriginalFilename());
            buffIn = new BufferedInputStream(file.getInputStream());
            if (ftpClient.storeFile(file.getOriginalFilename(), buffIn) == true) {
                log.info("Upload file SUCCESS: " + fileConfig.getRemoteFilePathSFTP() + file.getOriginalFilename());
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else if (ftpClient.getReplyCode() == 500) {
                log.error("Upload file ACCESS DENIED: " + fileConfig.getRemoteFilePathSFTP() + file.getOriginalFilename());
                lpbResCode.setErrorCode(ErrorMessage.ACCESS_DENIED.label);
                lpbResCode.setErrorDesc(ErrorMessage.ACCESS_DENIED.description);
            } else if (ftpClient.getReplyCode() == 550) {
                log.error("Upload file File directory name, or volume label syntax is incorrect: "
                    + fileConfig.getRemoteFilePathSFTP() + file.getOriginalFilename());
                lpbResCode.setErrorCode(ErrorMessage.FILE_NOT_SYNTAX_INCORRECT.label);
                lpbResCode.setErrorDesc(ErrorMessage.FILE_NOT_SYNTAX_INCORRECT.description);
            } else {
                log.error("Upload file FAIL: " + fileConfig.getRemoteFilePathSFTP() + file.getOriginalFilename());
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            if (buffIn != null)
                buffIn.close();
        } catch (Exception ex) {
            log.error("Upload file fail: " + ex);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public FTPFile[] getListFile() throws IOException {
        System.setProperty("jdk.tls.useExtendedMasterSecret", "false");

        FTPSClient ftpClient = new FTPSClient("TLS", false);
        ftpClient.addProtocolCommandListener(
            new PrintCommandListener(
                new PrintWriter(new OutputStreamWriter(System.out, "UTF-8")), true));
        try {
            ftpClient.connect(fileConfig.getHostSFTP(), fileConfig.getPortSFTP());
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.error("FTP server refused connection.");
            }
            ftpClient.setBufferSize(10000);
            ftpClient.enterLocalPassiveMode();
            ftpClient.login(fileConfig.getUserSFTP(), fileConfig.getPasswordSFTP());
            ftpClient.execPBSZ(0);
            ftpClient.execPROT("P");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(fileConfig.getRemoteFilePathSFTP());
            FTPFile[] listFiles = ftpClient.listFiles();
            return listFiles;
        } catch (IOException ex) {
            log.error("Error get list files", ex);
            throw new RuntimeException("Error get list files. Error: " + ex.getMessage());
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    @Override
    public boolean downloadFile(String fileName) throws IOException {
        System.setProperty("jdk.tls.useExtendedMasterSecret", "false");

        Path rootDownloads = Paths.get(fileConfig.getRemoteFilePathUpload());
        FTPSClient ftpClient = new FTPSClient("TLS", false);
        ftpClient.addProtocolCommandListener(
            new PrintCommandListener(
                new PrintWriter(new OutputStreamWriter(System.out, "UTF-8")), true));
        try {
            ftpClient.connect(fileConfig.getHostSFTP(), fileConfig.getPortSFTP());
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.error("FTP server refused connection.");
            }
            log.info("download file " + fileConfig.getRemoteFilePathSFTP() + fileName);
            ftpClient.setBufferSize(10000);
            ftpClient.enterLocalPassiveMode();
            ftpClient.login(fileConfig.getUserSFTP(), fileConfig.getPasswordSFTP());
            ftpClient.execPBSZ(0);
            ftpClient.execPROT("P");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(fileConfig.getRemoteFilePathSFTP());
            FTPFile[] remoteFiles = ftpClient.listFiles(fileName);
            if (remoteFiles.length != 0) {
                FileOutputStream fout = null;
                try {
                    if (!Files.exists(rootDownloads)) {
                        Files.createDirectory(rootDownloads);
                    }
                    fout = new FileOutputStream(rootDownloads + "/" + fileName);
                    if (ftpClient.retrieveFile(fileName, fout) == true) {
                        log.info("download file successful");
                        return true;
                    } else {
                        log.error("Error download file");
                        throw new RuntimeException("Error download file. Error: ");
                    }
                } catch (Exception e) {
                    log.error("Error download file", e);
                    throw new RuntimeException("Error download file. Error: " + e.getMessage());
                } finally {
                    fout.close();
                }
            } else {
                log.error("File does not exists " + fileName);
                return false;
            }
        } catch (IOException ex) {
            log.error("Error download file", ex);
            throw new RuntimeException("Error download file. Error: " + ex.getMessage());
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    @Override
    public boolean checkFile(String fileName) throws IOException {
        System.setProperty("jdk.tls.useExtendedMasterSecret", "false");

        FTPSClient ftpClient = new FTPSClient("TLS", false);
        ftpClient.addProtocolCommandListener(
            new PrintCommandListener(
                new PrintWriter(new OutputStreamWriter(System.out, "UTF-8")), true));
        try {
            ftpClient.connect(fileConfig.getHostSFTP(), fileConfig.getPortSFTP());
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.error("FTP server refused connection.");
            }
            log.info("check file " + fileConfig.getRemoteFilePathSFTP() + fileName);
            ftpClient.setBufferSize(10000);
            ftpClient.enterLocalPassiveMode();
            ftpClient.login(fileConfig.getUserSFTP(), fileConfig.getPasswordSFTP());
            ftpClient.execPBSZ(0);
            ftpClient.execPROT("P");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(fileConfig.getRemoteFilePathSFTP());
            FTPFile[] remoteFiles = ftpClient.listFiles(fileName);
            if (remoteFiles.length != 0) {
                return true;
            } else {
                log.error("File does not exists " + fileName);
                return false;
            }
        } catch (IOException ex) {
            log.error("Error download file", ex);
            throw new RuntimeException("Error download file. Error: " + ex.getMessage());
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}
