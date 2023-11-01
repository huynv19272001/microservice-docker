package com.lpb.napas.ecom.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.napas.ecom.dto.*;
import com.lpb.napas.ecom.model.EsbSystemEcomLog;

import java.util.List;

public interface IInitTransactionProcess {
    ResponseModel executeInitTransactionRequest(TransactionDTO transactionDTO);

    ResponseModel executeGetInitTransactionRequest(EsbSystemEcomLog esbSystemEcomLog);

    ResponseModel executeUploadTransferJrnRequest(UploadTransferJRNDTO uploadTransferJRNDTO) throws JsonProcessingException;

    UploadTransferJRNDTO initUploadTransferJRNDTO(List<TransactionPostInfoDTO> listTransactionInitDTO, EsbSystemEcomLog esbSystemEcomLog, PurchaseRequest purchaseRequest);

    List<PostInfoDTO> initListPostInfoDTO(DataVerifyPaymentRequest dataVerifyPaymentRequest,
                                          CardInfoDTO cardInfoDTO,
                                          GetAvlBalanceRESDTO getAvlBalanceRES);

    ServiceDTO initServiceDTO(DataVerifyPaymentRequest dataVerifyPaymentRequest);

    PartnerDTO initPartnerDTO(DataVerifyPaymentRequest dataVerifyPaymentRequest, String appMsgId, VerifyPaymentRequest verifyPaymentRequest);

    // AccountInfoDTO initAccountInfoDTO(DataVerifyPaymentRequest dataVerifyPaymentRequest, DebitCardInfoResponse debitCardInfoResponse);

    TransactionInfoDTO initTransactionInfoDTO(EsbSystemEcomLog esbSystemEcomLog, DataVerifyPaymentRequest
        dataVerifyPaymentRequest, CardInfoDTO cardInfoDTO, GetAvlBalanceRESDTO getAvlBalanceRESDTO);

    TransactionDTO initTransactionDTO(EsbSystemEcomLog esbSystemEcomLog, VerifyPaymentRequest verifyPaymentRequest,
                                      CardInfoDTO cardInfoDTO, DataVerifyPaymentRequest dataVerifyPaymentRequest,
                                      GetAvlBalanceRESDTO getAvlBalanceRESDTO);
}
