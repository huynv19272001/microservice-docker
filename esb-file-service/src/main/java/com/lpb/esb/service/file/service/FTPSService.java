package com.lpb.esb.service.file.service;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FTPSService {
    ResponseModel uploadFile(MultipartFile file, BaseRequestDTO request, ServiceInfo serviceInfo);

    ResponseModel getListFile(BaseRequestDTO request, ServiceInfo serviceInfo);

    ResponseModel checkFile(BaseRequestDTO request, ServiceInfo serviceInfo);

    ResponseModel downloadFile(BaseRequestDTO request, ServiceInfo serviceInfo);
}
