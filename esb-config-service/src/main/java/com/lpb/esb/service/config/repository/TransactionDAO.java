package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.request.transaction.UpdateTransactionDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface TransactionDAO {
    ResponseModel initTransaction(String userID, String trnBranch, String trnDesc,
                                  String appMsgId, String xmltypeCustomerInfo,
                                  String xmlTypePartnerInfo, String xmlTypeService,
                                  String xmlTypeTrnPost, BaseRequestDTO baseRequestDTO);

    ResponseModel updateTransaction(UpdateTransactionDTO transactionDTO, String msgId);
}
