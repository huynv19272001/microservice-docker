package com.lpb.esb.settle.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.request.BodyInfoDTO;
import com.lpb.esb.service.common.model.request.HeaderInfoDTO;
import com.lpb.esb.service.common.model.request.settle.DataSettleDTO;
import com.lpb.esb.service.common.model.request.transaction.UpdateSettleBillDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.service.TransactionService;
import com.lpb.esb.service.common.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@Log4j2
public class UpdateTrans {
    @Autowired
    RestTemplate restTemplateLB;

    @Async
    public void updateTrans(DataSettleDTO RES, DataSettleDTO REQ, String appMsgId, LpbResCode lpbResCode) {
        try {
            ObjectMapper obj = new ObjectMapper();
            log.info("Start updateTrans: " + appMsgId + "-" + REQ.getHeader().getMsgid());
            ResponseModel responseModel = TransactionService.updateSettleBill(restTemplateLB, mapObject(REQ, RES, appMsgId, lpbResCode));
            log.info("Response updateTrans: " + appMsgId + "-" + REQ.getHeader().getMsgid() + "-" + obj.writeValueAsString(responseModel));
            log.info("End updateTrans: " + appMsgId + "-" + REQ.getHeader().getMsgid());

        } catch (Exception e) {
            e.printStackTrace();
            log.info("updateTrans exception: " + e);
        }
    }

    private BaseRequestDTO mapObject(DataSettleDTO REQ, DataSettleDTO RES, String appMsgId, LpbResCode lpbResCode) {
        String errorCode;
        if (lpbResCode.getErrorCode().equals("00")) {
            errorCode = "ESB-000";
        } else if (lpbResCode.getErrorCode().equals("47") || lpbResCode.getErrorCode().equals("90") || lpbResCode.getErrorCode().equals("99")) {
            errorCode = "ESB-090";
        } else {
            errorCode = "ESB-099";
        }
        String billingLog = null;
        if (!StringUtils.isNullOrBlank(RES.getBody().getSettleBill().getServiceLog().getBillingLog())) {
            billingLog = RES.getBody().getSettleBill().getServiceLog().getBillingLog();
        }
        HeaderInfoDTO headerInfoDTO = HeaderInfoDTO.builder()
            .source(REQ.getHeader().getSource())
            .ubsComp(REQ.getHeader().getUbscomp())
            .referenceNo(REQ.getHeader().getReferenceNo())
            .msgId(REQ.getHeader().getMsgid())
            .correlId(REQ.getHeader().getCorrelid())
            .userId(REQ.getHeader().getUserid())
            .branch(REQ.getHeader().getBranch())
            .moduleId(REQ.getHeader().getModuleid())
            .serviceId(REQ.getHeader().getServiceId())
            .service(REQ.getHeader().getService())
            .operation(REQ.getHeader().getOperation())
            .productCode(REQ.getHeader().getProductCode())
            .sourceOperation(REQ.getHeader().getSourceOperation())
            .sourceUserId(REQ.getHeader().getSourceUserid())
            .destination(REQ.getHeader().getDestination())
            .multiTripId(REQ.getHeader().getMultitripid())
            .functionId(REQ.getHeader().getFunctionid())
            .action(REQ.getHeader().getAction())
            .build();

        UpdateSettleBillDTO updateSettleBillDTO = UpdateSettleBillDTO.builder()
            .transactionId(appMsgId)
            .valueDt(new Date())
            .serviceInfo(billingLog)
            .errorCode(errorCode)
            .errorDesc(lpbResCode.getErrorDesc())
            .build();

        BodyInfoDTO bodyInfoDTO = BodyInfoDTO.builder()
            .data(updateSettleBillDTO)
            .build();

        BaseRequestDTO baseRequestDTO = BaseRequestDTO.builder()
            .header(headerInfoDTO)
            .body(bodyInfoDTO)
            .build();
        log.info("Request updateTrans: " + appMsgId + "-" + REQ.getHeader().getMsgid() + baseRequestDTO);

        return baseRequestDTO;
    }
}
