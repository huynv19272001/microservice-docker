package com.lpb.esb.service.file.service;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.file.config.FileConfig;
import com.lpb.esb.service.file.dto.FileInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class FTPServiceImpl implements FTPService {
    @Autowired
    FileConfig fileConfig;

    @Override
    public ResponseModel uploadFile(MultipartFile file, BaseRequestDTO request, ServiceInfo serviceInfo) {
        FTPClient ftpClient = new FTPClient();
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        BufferedInputStream buffIn = null;
        try {
            initFTPClient(ftpClient, serviceInfo, request);
            buffIn = new BufferedInputStream(file.getInputStream());
            if (ftpClient.storeFile(file.getOriginalFilename(), buffIn) == true) {
                log.info(request.getHeader().getMsgId() + " Upload file SUCCESS: " + serviceInfo.getUdf3() + file.getOriginalFilename());
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else if (ftpClient.getReplyCode() == 500) {
                log.error(request.getHeader().getMsgId() + " Upload file ACCESS DENIED: " + serviceInfo.getUdf3() + file.getOriginalFilename());
                lpbResCode.setErrorCode(ErrorMessage.ACCESS_DENIED.label);
                lpbResCode.setErrorDesc(ErrorMessage.ACCESS_DENIED.description);
            } else if (ftpClient.getReplyCode() == 550) {
                log.error(request.getHeader().getMsgId() + " Upload file File directory name, or volume label syntax is incorrect: "
                    + serviceInfo.getUdf3() + file.getOriginalFilename());
                lpbResCode.setErrorCode(ErrorMessage.FILE_NOT_SYNTAX_INCORRECT.label);
                lpbResCode.setErrorDesc(ErrorMessage.FILE_NOT_SYNTAX_INCORRECT.description);
            } else {
                log.error(request.getHeader().getMsgId() + " Upload file FAIL: " + serviceInfo.getUdf3() + file.getOriginalFilename());
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            if (buffIn != null)
                buffIn.close();
        } catch (Exception ex) {
            log.error(request.getHeader().getMsgId() + " Upload file fail: " + ex);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
        } finally {
            closeFTPClient(ftpClient, request);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public ResponseModel getListFile(BaseRequestDTO request, ServiceInfo serviceInfo) {
        FTPClient ftpClient = new FTPClient();
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            initFTPClient(ftpClient, serviceInfo, request);
            FTPFile[] listFiles = ftpClient.listFiles();
            if (listFiles.length == 0) {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            } else {
                List<FileInfo> fileInfos = new ArrayList<>();
                for (FTPFile file : listFiles) {
                    if (file.isFile()) {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setName(file.getName());
                        fileInfos.add(fileInfo);
                    }
                }
                responseModel.setData(fileInfos);
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            }
        } catch (Exception ex) {
            log.error(request.getHeader().getMsgId() + " Get list file fail: " + ex);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
        } finally {
            closeFTPClient(ftpClient, request);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public ResponseModel checkFile(BaseRequestDTO request, ServiceInfo serviceInfo) {
        FTPClient ftpClient = new FTPClient();
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            initFTPClient(ftpClient, serviceInfo, request);
            FTPFile[] listFiles = ftpClient.listFiles(request.getHeader().getDestination());
            if (listFiles.length == 0) {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc("File does not exists");
            } else {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc("File exists");
            }
        } catch (Exception ex) {
            log.error(request.getHeader().getMsgId() + " Get list file fail: " + ex);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
        } finally {
            closeFTPClient(ftpClient, request);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public ResponseModel downloadFile(BaseRequestDTO request, ServiceInfo serviceInfo) {
        FTPClient ftpClient = new FTPClient();
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        Path rootDownloads = Paths.get(fileConfig.getRemoteFilePathUpload());
        try {
            initFTPClient(ftpClient, serviceInfo, request);
            FTPFile[] listFiles = ftpClient.listFiles(request.getHeader().getDestination());
            if (listFiles.length == 0) {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc("File does not exists");
            } else {
                FileOutputStream fout = null;
                try {
                    if (!Files.exists(rootDownloads)) {
                        Files.createDirectory(rootDownloads);
                    }
                    fout = new FileOutputStream(rootDownloads + "/" + request.getHeader().getDestination());
                    if (ftpClient.retrieveFile(request.getHeader().getDestination(), fout) == true) {
                        log.info(request.getHeader().getMsgId() + " download file successful");
                        lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                        lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                    } else {
                        log.info(request.getHeader().getMsgId() + " download file fail");
                        lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                        lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
                    }
                } catch (Exception e) {
                    log.info(request.getHeader().getMsgId() + " download file successful: " + e);
                    lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                    lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
                } finally {
                    fout.close();
                }
            }
        } catch (Exception ex) {
            log.error(request.getHeader().getMsgId() + " download file fail: " + ex);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
        } finally {
            closeFTPClient(ftpClient, request);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    private FTPClient initFTPClient(FTPClient ftpClient, ServiceInfo serviceInfo, BaseRequestDTO request) {
        try {
            ftpClient.addProtocolCommandListener(
                new PrintCommandListener(
                    new PrintWriter(new OutputStreamWriter(System.out, "UTF-8")), true));
            ftpClient.connect(serviceInfo.getConnectorURL(), Integer.parseInt(serviceInfo.getConnectPort()));
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.error(request.getHeader().getMsgId() + " FTP server refused connection.");
            }
            ftpClient.setBufferSize(10000);
            ftpClient.login(serviceInfo.getUdf1(), serviceInfo.getUdf2());
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(serviceInfo.getUdf3());
            return ftpClient;
        } catch (Exception e) {
            log.info(request.getHeader().getMsgId() + " Connect FTP fails: " + e.getMessage());
            return null;
        }
    }

    private void closeFTPClient(FTPClient ftpClient, BaseRequestDTO request) {
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (Exception e) {
            log.info(request.getHeader().getMsgId() + " Close FTP fails: " + e.getMessage());
        }
    }
}
