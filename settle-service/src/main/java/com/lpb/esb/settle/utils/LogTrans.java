package com.lpb.esb.settle.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.request.BodyInfoDTO;
import com.lpb.esb.service.common.model.request.HeaderInfoDTO;
import com.lpb.esb.service.common.model.request.settle.DataSettleDTO;
import com.lpb.esb.service.common.model.request.transaction.BillingLogDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class LogTrans {
    @Autowired
    RestTemplate restTemplateLB;

    @Async
    public void logTrans(DataSettleDTO RES, DataSettleDTO REQ, String appMsgId) {
        try {
            ObjectMapper obj = new ObjectMapper();
            log.info("Start insert logTrans DB: " + appMsgId + "-" + REQ.getHeader().getMsgid());
            BillingLogDTO billingLogDTO = RES.getBody().getSettleBill().getServiceLog();
            ResponseModel responseModel = TransactionService.billingLog(restTemplateLB, mapObject(REQ, billingLogDTO, appMsgId));
            log.info("Response insert logTrans DB:  " + appMsgId + "-" + REQ.getHeader().getMsgid() + "-" + obj.writeValueAsString(responseModel));
            log.info("End insert logTrans DB" + appMsgId + "-" + REQ.getHeader().getMsgid());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("logTrans exception: " + e);
        }
    }

    private BaseRequestDTO mapObject(DataSettleDTO REQ, BillingLogDTO billingLogDTO, String appMsgId) {
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

        BodyInfoDTO bodyInfoDTO = BodyInfoDTO.builder()
            .data(billingLogDTO)
            .build();

        BaseRequestDTO baseRequestDTO = BaseRequestDTO.builder()
            .header(headerInfoDTO)
            .body(bodyInfoDTO)
            .build();
        log.info("Request insert logTrans DB: " + appMsgId + "-" + REQ.getHeader().getMsgid() + baseRequestDTO);
        return baseRequestDTO;
    }
}
