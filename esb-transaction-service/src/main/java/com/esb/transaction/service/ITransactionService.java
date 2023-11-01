package com.esb.transaction.service;

import com.esb.transaction.dto.UpdateTransactionDTO;
import com.esb.transaction.dto.UploadTransferJRNDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;
import org.w3c.dom.Document;


public interface ITransactionService {
    ResponseModel initTransaction(String userID, String trnBranch, String trnDesc,
                                  String appMsgId, String xmltypeCustomerInfo,
                                  String xmlTypePartnerInfo, String xmlTypeService,
                                  String xmlTypeTrnPost);

    ResponseModel updateTransaction(UpdateTransactionDTO transactionDTO);

    ResponseModel loadTransactionPost(String appMsgId);

    ResponseModel getResponseModelUploadTransferJRN(Document doc);

    ResponseModel uploadTransferJRN(UploadTransferJRNDTO uploadTransferIO);

    /**
     * @author Trung.Nguyen
     * @date 15-Sep-2022
     * */
    public ResponseModel uploadTransferCitad(UploadTransferJRNDTO uploadTransferIO);

}
