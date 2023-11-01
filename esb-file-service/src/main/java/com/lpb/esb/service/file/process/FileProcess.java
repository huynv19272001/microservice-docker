package com.lpb.esb.service.file.process;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;
import org.springframework.web.multipart.MultipartFile;

public interface FileProcess {
    ResponseModel uploadFile(MultipartFile file, BaseRequestDTO request);

    ResponseModel getListFile(BaseRequestDTO request);

    ResponseModel checkFile(BaseRequestDTO request);

    ResponseModel downloadFile(BaseRequestDTO request);
}
