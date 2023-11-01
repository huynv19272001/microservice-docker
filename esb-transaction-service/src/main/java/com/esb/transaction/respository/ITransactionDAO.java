package com.esb.transaction.respository;

import com.esb.transaction.dto.UpdateTransactionDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;

public interface ITransactionDAO {
    ResponseModel updateTransaction(UpdateTransactionDTO transactionDTO);

    ResponseModel initTransaction(String userID, String trnBranch, String trnDesc,
                                  String appMsgId, String xmltypeCustomerInfo,
                                  String xmlTypePartnerInfo, String xmlTypeService,
                                  String xmlTypeTrnPost);
}
