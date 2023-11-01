package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.request.transaction.UpdateSettleBillDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface SettleBillDAO {
    ResponseModel switchingEsb(String userid, String trnBrn, String trnDesc, String appMsgId, String xmlTypeCustomer,
                               String confirmReq, String xmlTypePartnerInfo, String xmlTypeService, String txnCode, BaseRequestDTO request);

    ResponseModel updateSettleBill(UpdateSettleBillDTO updateSettleBillDTO, String xmlServiceInfo,
                                   String serviceInfo, BaseRequestDTO request);

    void initPayBill(String rowId, String serviceId);

    ResponseModel billingLog(BaseRequestDTO baseRequestDTO);

}
