package com.lpb.service.sql.process;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.service.sql.utils.constants.Constants;
import com.lpb.service.sql.utils.constants.Message;
import com.lpb.service.sql.model.RequestDTO;
import com.lpb.service.sql.service.IDMLService;
import com.lpb.service.sql.service.ITCLService;
import com.lpb.service.sql.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Log4j2
@Service
public class ProcessImpl implements Process {
    @Autowired
    IDMLService dmlService;

    @Autowired
    ITCLService tclService;

    @Override
    public ResponseModel executeProcess(RequestDTO requestDTO){
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        if (StringUtils.isNullOrBlank(requestDTO.getHeader().getServiceId()) || StringUtils.isNullOrBlank(requestDTO.getHeader().getOperation()) || StringUtils.isNullOrBlank(requestDTO.getHeader().getProductCode()) || StringUtils.isNullOrBlank(requestDTO.getHeader().getSource()) || StringUtils.isNullOrBlank(requestDTO.getHeader().getBranch()) || StringUtils.isNullOrBlank(requestDTO.getHeader().getMsgId()) || requestDTO.getBody().getParams().size() <= 0) {
            resCode = LpbResCode.builder().errorCode(Message.MSG_011.getErrorCode()).errorDesc(Message.MSG_011.getErrorDesc()).build();
            responseModel = ResponseModel.builder().resCode(resCode).build();
            return responseModel;
        }
        if (requestDTO.getHeader().getOperation().equals(Constants.OPERATION_DML)) {
            responseModel = dmlService.startExecute(requestDTO);
        } else if (requestDTO.getHeader().getOperation().equals(Constants.OPERATION_TCL)) {
            responseModel = tclService.startExecute(requestDTO);
        } else {
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.INVALID.label).errorDesc("OPERATION INVALID!").build();
            responseModel = ResponseModel.builder().resCode(resCode).build();
        }
        return responseModel;
    }
}
