package com.lpb.esb.service.file.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@Log4j2
public class SFTPServiceImpl implements SFTPService {

    @Override
    public ResponseModel uploadFile(MultipartFile file, BaseRequestDTO request, ServiceInfo serviceInfo) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ChannelSftp channelSftp = setupJsch(serviceInfo);
        try {
            channelSftp.connect();
            InputStream inputStream = file.getInputStream();
            String remoteDir = serviceInfo.getUdf3();
            log.info(remoteDir + file.getOriginalFilename());
            channelSftp.put(inputStream, remoteDir + file.getOriginalFilename());
            lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
            lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
        } catch (Exception ex) {
            log.error(request.getHeader().getMsgId() + " Upload file fail: " + ex);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
        } finally {
            disconnectJsch(channelSftp);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }


    @Override
    public ResponseModel getListFile(BaseRequestDTO request, ServiceInfo serviceInfo) {
        return null;
    }

    @Override
    public ResponseModel checkFile(BaseRequestDTO request, ServiceInfo serviceInfo) {
        return null;
    }

    @Override
    public ResponseModel downloadFile(BaseRequestDTO request, ServiceInfo serviceInfo) {
        return null;
    }

    private void disconnectJsch(ChannelSftp channelSftp) {
        try {
            channelSftp.exit();
            channelSftp.getSession().disconnect();
        } catch (Exception e) {
            log.info("Exception setupJsch: " + e.getMessage());
        }
    }

    private ChannelSftp setupJsch(ServiceInfo serviceInfo) {
        try {
            JSch jsch = new JSch();
            Session jschSession = jsch.getSession(serviceInfo.getUdf1(), serviceInfo.getConnectorURL(), Integer.parseInt(serviceInfo.getConnectPort()));
            jschSession.setConfig("StrictHostKeyChecking", "no");
            jschSession.setPassword(serviceInfo.getUdf2());
            jschSession.connect();
            return (ChannelSftp) jschSession.openChannel("sftp");
        } catch (Exception e) {
            log.info("Exception setupJsch: " + e.getMessage());
            return null;
        }
    }
}
