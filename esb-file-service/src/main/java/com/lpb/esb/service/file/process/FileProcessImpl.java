package com.lpb.esb.service.file.process;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.file.service.FTPSService;
import com.lpb.esb.service.file.service.FTPService;
import com.lpb.esb.service.file.service.SFTPService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
@Service
public class FileProcessImpl implements FileProcess {
    @Autowired
    RestTemplate restTemplateLB;

    @Autowired
    FTPService ftpService;

    @Autowired
    FTPSService ftpsService;

    @Autowired
    SFTPService sftpService;

    @Override
    public ResponseModel uploadFile(MultipartFile file, BaseRequestDTO request) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            //gọi thông tin kết nối
            List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB, request.getHeader().getServiceId(),
                request.getHeader().getProductCode(), request.getHeader().getOperation());
            if (list == null || list.isEmpty()) {
                lpbResCode.setErrorCode(ErrorMessage.SERVICE_NOT_EXITS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SERVICE_NOT_EXITS.description);
                responseModel.setResCode(lpbResCode);
                return responseModel;
            }
            String type = list.get(0).getMethodAction();
            switch (type) {
                case "FTPS":
                    log.info(request.getHeader().getMsgId() + " Upload file FTPS");
                    responseModel = ftpsService.uploadFile(file, request, list.get(0));
                    break;
                case "FTP":
                    log.info(request.getHeader().getMsgId() + " Upload file FTP");
                    responseModel = ftpService.uploadFile(file, request, list.get(0));
                    break;
                case "SFTP":
                    log.info(request.getHeader().getMsgId() + " Upload file SFTP");
                    responseModel = sftpService.uploadFile(file, request, list.get(0));
                    break;
                default:
                    log.info(request.getHeader().getMsgId() + " Upload file FTP");
                    responseModel = ftpService.uploadFile(file, request, list.get(0));
            }
        } catch (Exception ex) {
            log.error(request.getHeader().getMsgId() + " Upload file fail: " + ex);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    @Override
    public ResponseModel getListFile(BaseRequestDTO request) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            //gọi thông tin kết nối
            List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB, request.getHeader().getServiceId(),
                request.getHeader().getProductCode(), request.getHeader().getOperation());
            if (list == null || list.isEmpty()) {
                lpbResCode.setErrorCode(ErrorMessage.SERVICE_NOT_EXITS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SERVICE_NOT_EXITS.description);
                responseModel.setResCode(lpbResCode);
                return responseModel;
            }
            String type = list.get(0).getMethodAction();
            switch (type) {
                case "FTPS":
                    log.info(request.getHeader().getMsgId() + " GetListFile file FTPS");
                    responseModel = ftpsService.getListFile(request, list.get(0));
                    break;
                case "FTP":
                    log.info(request.getHeader().getMsgId() + " GetListFile file FTP");
                    responseModel = ftpService.getListFile(request, list.get(0));
                    break;
                default:
                    log.info(request.getHeader().getMsgId() + " GetListFile file FTP");
                    responseModel = ftpService.getListFile(request, list.get(0));
            }
        } catch (Exception ex) {
            log.error(request.getHeader().getMsgId() + " GetListFile file fail: " + ex);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    @Override
    public ResponseModel checkFile(BaseRequestDTO request) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            //gọi thông tin kết nối
            List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB, request.getHeader().getServiceId(),
                request.getHeader().getProductCode(), request.getHeader().getOperation());
            if (list == null || list.isEmpty()) {
                lpbResCode.setErrorCode(ErrorMessage.SERVICE_NOT_EXITS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SERVICE_NOT_EXITS.description);
                responseModel.setResCode(lpbResCode);
                return responseModel;
            }
            String type = list.get(0).getMethodAction();
            switch (type) {
                case "FTPS":
                    log.info(request.getHeader().getMsgId() + " CheckFile FTPS");
                    responseModel = ftpsService.checkFile(request, list.get(0));
                    break;
                case "FTP":
                    log.info(request.getHeader().getMsgId() + " CheckFile FTP");
                    responseModel = ftpService.checkFile(request, list.get(0));
                    break;
                default:
                    log.info(request.getHeader().getMsgId() + " CheckFile FTP");
                    responseModel = ftpService.checkFile(request, list.get(0));
            }
        } catch (Exception ex) {
            log.error(request.getHeader().getMsgId() + " CheckFile fail: " + ex);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    @Override
    public ResponseModel downloadFile(BaseRequestDTO request) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            //gọi thông tin kết nối
            List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB, request.getHeader().getServiceId(),
                request.getHeader().getProductCode(), request.getHeader().getOperation());
            if (list == null || list.isEmpty()) {
                lpbResCode.setErrorCode(ErrorMessage.SERVICE_NOT_EXITS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SERVICE_NOT_EXITS.description);
                responseModel.setResCode(lpbResCode);
                return responseModel;
            }
            String type = list.get(0).getMethodAction();
            switch (type) {
                case "FTPS":
                    log.info(request.getHeader().getMsgId() + " DownloadFile FTPS");
                    responseModel = ftpsService.downloadFile(request, list.get(0));
                    break;
                case "FTP":
                    log.info(request.getHeader().getMsgId() + " DownloadFile FTP");
                    responseModel = ftpService.downloadFile(request, list.get(0));
                    break;
                default:
                    log.info(request.getHeader().getMsgId() + " DownloadFile FTP");
                    responseModel = ftpService.downloadFile(request, list.get(0));
            }
        } catch (Exception ex) {
            log.error(request.getHeader().getMsgId() + " DownloadFile fail: " + ex);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }
}
