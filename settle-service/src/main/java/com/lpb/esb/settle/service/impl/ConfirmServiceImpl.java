package com.lpb.esb.settle.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.request.BodyInfoDTO;
import com.lpb.esb.service.common.model.request.HeaderInfoDTO;
import com.lpb.esb.service.common.model.request.infocustomerbill.BillDTO;
import com.lpb.esb.service.common.model.request.infocustomerbill.CustomerDTO;
import com.lpb.esb.service.common.model.request.infocustomerbill.HeaderDTO;
import com.lpb.esb.service.common.model.request.settle.DataSettleDTO;
import com.lpb.esb.service.common.model.request.settle.SettleBillDTO;
import com.lpb.esb.service.common.model.request.transaction.*;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.service.TransactionService;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.settle.service.ConfirmService;
import com.lpb.esb.settle.utils.LogTrans;
import com.lpb.esb.settle.utils.UpdateTrans;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class ConfirmServiceImpl implements ConfirmService {

    @Autowired
    RestTemplate restTemplateLB;

    @Autowired
    LogTrans logTrans;

    @Autowired
    UpdateTrans updateTrans;

    private BaseRequestDTO mapObjectRQ(DataSettleDTO REQ) {
        BaseRequestDTO baseRequestDTO = new BaseRequestDTO();

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

        SettleBillDTO settleBillDTO = REQ.getBody().getSettleBill();
        RequestTransactionDTO request = RequestTransactionDTO.builder()
            .trnBrn(settleBillDTO.getTrnBrn())
            .customerNo(settleBillDTO.getCustomerNo())
            .userId(settleBillDTO.getUserId())
            .trnDesc(settleBillDTO.getTrnDesc())
            .transactionId(settleBillDTO.getTransactionId())
            .confirmTrn(settleBillDTO.getConfirmTrn())
            .build();
        if (settleBillDTO.getService() != null) {
            ServiceDTO serviceDTO = ServiceDTO.builder()
                .serviceId(settleBillDTO.getService().getServiceId())
                .productCode(settleBillDTO.getService().getProductCode())
                .merchantId(settleBillDTO.getService().getMerchantId())
                .receiveAccount(settleBillDTO.getService().getReceiveAccount())
                .requestAccount(settleBillDTO.getService().getRequestAccount())
                .build();
            request.setServiceInfo(serviceDTO);
        }
        if (settleBillDTO.getPartner() != null) {
            PartnerDTO partnerDTO = PartnerDTO.builder()
                .chanel(settleBillDTO.getPartner().getChanel())
                .terminalId(settleBillDTO.getPartner().getTerminalId())
                .txnCode(settleBillDTO.getPartner().getTxnCode())
                .txnConfirmDt(settleBillDTO.getPartner().getTxnConfirmDt())
                .txnDatetime(settleBillDTO.getPartner().getTxnDatetime())
                .txnRefNo(settleBillDTO.getPartner().getTxnRefNo())
                .build();
            request.setPartnerInfo(partnerDTO);
        }
        if (settleBillDTO.getListPostInfo() != null) {
            List<PostInfoDTO> ListPostInfo = new ArrayList<>();
            for (com.lpb.esb.service.common.model.request.settle.PostInfoDTO Post : settleBillDTO.getListPostInfo()) {
                PostInfoDTO postInfoDTO = PostInfoDTO.builder()
                    .sourceAcc(Post.getSourceAcc())
                    .acNo(Post.getAcNo())
                    .branchCode(Post.getBranchCode())
                    .ccy(Post.getCcy())
                    .fcyAmount(Post.getFcyAmount())
                    .lcyAmount(Post.getLcyAmount())
                    .drcrInd(Post.getDrcrInd())
                    .amountTag(Post.getAmountTag())
                    .bankCode(Post.getBankCode())
                    .bankName(Post.getBankName())
                    .makerId(Post.getMakerid())
                    .checkerId(Post.getCheckerid())
                    .infoSourceAcc(Post.getInfoSourceAcc())
                    .infoAcNo(Post.getInfoAcNo())
                    .postDesc(Post.getPostDesc())
                    .postRefNo(Post.getPostRefNo())
                    .build();
                ListPostInfo.add(postInfoDTO);
            }
            request.setPostInfo(ListPostInfo);
        }
        if (settleBillDTO.getCustomerInfo() != null) {
            com.lpb.esb.service.common.model.request.transaction.CustomerDTO customerInfo =
                com.lpb.esb.service.common.model.request.transaction.CustomerDTO.builder()
                    .userId(settleBillDTO.getCustomerInfo().getCustId())
                    .customerNo(settleBillDTO.getCustomerInfo().getCustomerNo())
                    .kindOfOtp(settleBillDTO.getCustomerInfo().getKindOfOtp())
                    .build();
            request.setCustomerInfo(customerInfo);
        }

        BodyInfoDTO bodyInfoDTO = BodyInfoDTO.builder()
            .data(request)
            .build();
        baseRequestDTO.setHeader(headerInfoDTO);
        baseRequestDTO.setBody(bodyInfoDTO);
        return baseRequestDTO;
    }

    private void mapObjectRS(DataSettleDTO dataSettleDTO, List<SettleBillingDTO> listBilling) {
        SettleBillDTO settleBillDTO = dataSettleDTO.getBody().getSettleBill();
        for (SettleBillingDTO billing : listBilling) {
            settleBillDTO.setConfirmTrn(billing.getConfirmTrn());
            settleBillDTO.setCustomerNo(billing.getCustomerNo());
            settleBillDTO.setTrnBrn(billing.getTrnBranch());
            settleBillDTO.setTrnDesc(billing.getTrnDesc());
            settleBillDTO.setTransactionId(billing.getTransactionId());

            PartnerDTO partnerInfo = billing.getPartnerInfo();
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

            com.lpb.esb.service.common.model.request.settle.PartnerDTO partner =
                com.lpb.esb.service.common.model.request.settle.PartnerDTO.builder()
                .txnRefNo(partnerInfo.getTxnRefNo())
                .txnDatetime(partnerInfo.getTxnDatetime())
                .txnCode(partnerInfo.getTxnCode())
                .chanel(partnerInfo.getChanel())
                .terminalId(partnerInfo.getTerminalId())
                .txnConfirmDt(timeStamp)
                .build();
            settleBillDTO.setPartner(partner);

            ServiceDTO serviceInfo = billing.getServiceInfo();
            com.lpb.esb.service.common.model.request.infocustomerbill.ServiceDTO service =
                com.lpb.esb.service.common.model.request.infocustomerbill.ServiceDTO.builder()
                .serviceId(serviceInfo.getServiceId())
                .productCode(serviceInfo.getProductCode())
                .requestAccount(serviceInfo.getRequestAccount())
                .receiveAccount(serviceInfo.getReceiveAccount())
                .merchantId(serviceInfo.getMerchantId())
                .build();
            settleBillDTO.setService(service);

            if (billing.getBillInfo() != null) {
                List<BillDTO> listBillInfo = new ArrayList<>();
                for (com.lpb.esb.service.common.model.request.transaction.BillDTO billDTO : billing.getBillInfo()) {
                    BillDTO billInfo = BillDTO.builder()
                        .billCode(billDTO.getBillCode())
                        .billId(billDTO.getBillId())
                        .billDesc(billDTO.getBillDesc())
                        .billType(billDTO.getBillType())
                        .billStatus(billDTO.getBillStatus())
                        .billAmount(billDTO.getBillAmount())
                        .paymentMethod(billDTO.getPaymentMethod())
                        .otherInfo(billDTO.getOtherInfo())
                        .amtUnit(billDTO.getAmtUnit())
                        .settledAmount(billDTO.getSettledAmount())
                        .build();
                    listBillInfo.add(billInfo);
                }
                settleBillDTO.setListBillInfo(listBillInfo);
            }

            CustomerDTO customerInfo = CustomerDTO.builder()
                .custId(billing.getServiceInfo().getRequestAccount())
                .customerNo(billing.getCustomerNo())
                .build();
            settleBillDTO.setCustomerInfo(customerInfo);
        }
    }

    @Override
    public ResponseModel excuteConfirmService(DataSettleDTO REQ) {
        ResponseModel responseModel;
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        DataSettleDTO RES = new DataSettleDTO();

        String response;
        HeaderDTO header = REQ.getHeader();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            log.info(header.getMsgid() + " Request SettleBill: " + objectMapper.writeValueAsString(REQ));
            //Get thông tin kết nối
            List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB, REQ.getBody().getSettleBill().getService().getServiceId(),
                REQ.getBody().getSettleBill().getService().getProductCode(), REQ.getHeader().getOperation());
            if (list.size() == 0) {
                log.info(header.getMsgid() + " Error RQ SettleBill: 493 - Dịch vụ Nhà cung cấp không hợp lệ");
                lpbResCode.setErrorCode("493");
                lpbResCode.setErrorDesc("Dịch vụ Nhà cung cấp không hợp lệ");
                responseModel = ResponseModel.builder().resCode(lpbResCode).data(REQ).build();
                return responseModel;
            }
            ServiceInfo serviceInfo = list.get(0);
            //get SettleBill
            responseModel = TransactionService.getSettleBill(restTemplateLB, mapObjectRQ(REQ));
            log.info(header.getMsgid() + "+ Response get transaction: " + objectMapper.writeValueAsString(responseModel));
            if (!responseModel.getResCode().getErrorCode().equals("00")) {
                log.info(REQ.getBody().getSettleBill().getTransactionId() + "-" + header.getMsgid() +
                    " Error RQ SettleBill: 285 - Mã giao dịch không tìm thấy: TransactionService.getSettleBill(restTemplateLB, baseRequestDTO)");
                lpbResCode.setErrorCode("031");
                lpbResCode.setErrorDesc("Mã giao dịch không tìm thấy");
                lpbResCode.setRefCode(responseModel.getResCode().getErrorCode());
                lpbResCode.setRefDesc(responseModel.getResCode().getErrorDesc());
                responseModel = ResponseModel.builder().resCode(lpbResCode).data(REQ).build();
                return responseModel;
            }
            List<SettleBillingDTO> listSettleBillingDTO = objectMapper.readValue
                (objectMapper.writeValueAsString(responseModel.getData()), new TypeReference<List<SettleBillingDTO>>() {
                });
            if (listSettleBillingDTO != null && listSettleBillingDTO.size() == 1) {
                mapObjectRS(REQ, listSettleBillingDTO);
            } else {
                log.info(REQ.getBody().getSettleBill().getTransactionId() + "-" + header.getMsgid() + " Error RQ SettleBill: 31 - Mã giao dịch không tìm thấy:" +
                    " TransactionService.getTransaction(restTemplateLB, baseRequestDTO)");
                lpbResCode.setErrorCode("31");
                lpbResCode.setErrorDesc("Mã giao dịch không tìm thấy: TransactionService.initSettleBill(restTemplateLB, baseRequestDTO)");
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
            //Call module to rq Partner
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            header.setDestination(serviceInfo.getConnectorURL());
            REQ.setHeader(header);
            log.info(header.getMsgid() + " Request call partner : " + objectMapper.writeValueAsString(REQ));
            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(REQ), headers);
            log.info(REQ.getBody().getSettleBill().getTransactionId() + "-" + header.getMsgid() + " Request call partner : " + entity);
            //Get response
            response = restTemplateLB.exchange(serviceInfo.getUrlApi(), HttpMethod.POST, entity, String.class).getBody();

            log.info(REQ.getBody().getSettleBill().getTransactionId() + "-" + header.getMsgid() + " Response call partner : " + response);

            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonResCode = jsonResponse.getJSONObject("resCode");
            JSONObject jsonData = jsonResponse.getJSONObject("data");
            RES = objectMapper.readValue(jsonData.toString(), DataSettleDTO.class);
            lpbResCode = objectMapper.readValue(jsonResCode.toString(), LpbResCode.class);
            log.info(REQ.getBody().getSettleBill().getTransactionId() + "-" + header.getMsgid() + " Response SettleBill: " + objectMapper.writeValueAsString(RES));

            //log giao dịch vào bảng esb_trans_log
            logTrans.logTrans(RES, REQ, REQ.getBody().getSettleBill().getTransactionId());
            //update trans
            updateTrans.updateTrans(RES, REQ, REQ.getBody().getSettleBill().getTransactionId(), lpbResCode);

            //set servicelog = null
//                RES.getBody().getSettleBill().setServiceLog(null);
        } catch (HttpClientErrorException e) {
            try {
                JSONObject jsonResponse = new JSONObject(e.getResponseBodyAsString());
                JSONObject jsonResCode = jsonResponse.getJSONObject("resCode");
                JSONObject jsonData = jsonResponse.getJSONObject("data");
                RES = objectMapper.readValue(jsonData.toString(), DataSettleDTO.class);
                lpbResCode = objectMapper.readValue(jsonResCode.toString(), LpbResCode.class);

                //log giao dịch vào bảng esb_trans_log
                logTrans.logTrans(RES, REQ, REQ.getBody().getSettleBill().getTransactionId());
                //update trans
                updateTrans.updateTrans(RES, REQ, REQ.getBody().getSettleBill().getTransactionId(), lpbResCode);

                //set servicelog = null
//                RES.getBody().getSettleBill().setServiceLog(null);
            } catch (Exception ex) {
                log.info(REQ.getBody().getSettleBill().getTransactionId() + "-" + header.getMsgid() + " Exception SettleBill: " + e);
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
        } catch (ResourceAccessException e) {
            log.info(REQ.getBody().getSettleBill().getTransactionId() + "-" + header.getMsgid() + " ResourceAccessException SettleBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(REQ.getBody().getSettleBill().getTransactionId() + "-" + header.getMsgid() + " Exception SettleBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel = ResponseModel.builder().resCode(lpbResCode).data(RES).build();
        log.info(REQ.getBody().getSettleBill().getTransactionId() + "-" + header.getMsgid() + " Response: " + responseModel);
        return responseModel;
    }
}
