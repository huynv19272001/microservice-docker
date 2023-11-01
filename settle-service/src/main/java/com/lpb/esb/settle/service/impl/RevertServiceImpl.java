package com.lpb.esb.settle.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.request.BodyInfoDTO;
import com.lpb.esb.service.common.model.request.HeaderInfoDTO;
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
import com.lpb.esb.settle.service.RevertService;
import com.lpb.esb.settle.utils.GetTranId;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class RevertServiceImpl implements RevertService {

    @Autowired
    RestTemplate restTemplateLB;
    @Autowired
    GetTranId getTranId;

    @Autowired
    LogTrans logTrans;

    @Autowired
    UpdateTrans updateTrans;

    private BaseRequestDTO mapObjectInit(DataSettleDTO REQ) {
        HeaderDTO headerDTO = REQ.getHeader();
        SettleBillDTO settleBillDTO = REQ.getBody().getSettleBill();
        BaseRequestDTO baseRequestDTO = new BaseRequestDTO();

        HeaderInfoDTO headerInfoDTO = HeaderInfoDTO.builder()
            .source(headerDTO.getSource())
            .ubsComp(headerDTO.getUbscomp())
            .referenceNo(headerDTO.getReferenceNo())
            .msgId(headerDTO.getMsgid())
            .correlId(headerDTO.getCorrelid())
            .userId(headerDTO.getUserid())
            .branch(headerDTO.getBranch())
            .moduleId(headerDTO.getModuleid())
            .serviceId(headerDTO.getServiceId())
            .service(headerDTO.getService())
            .operation(headerDTO.getOperation())
            .productCode(headerDTO.getProductCode())
            .sourceOperation(headerDTO.getSourceOperation())
            .sourceUserId(headerDTO.getSourceUserid())
            .destination(headerDTO.getDestination())
            .multiTripId(headerDTO.getMultitripid())
            .functionId(headerDTO.getFunctionid())
            .action(headerDTO.getAction())
            .build();

        BodyInfoDTO<Object> bodyInfoDTO = new BodyInfoDTO<>();
        RequestSettleBillDTO requestSettleBillDTO = RequestSettleBillDTO.builder()
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
            requestSettleBillDTO.setServiceInfo(serviceDTO);
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
            requestSettleBillDTO.setPartnerInfo(partnerDTO);
        }
        if (settleBillDTO.getListPostInfo() != null) {
            List<PostInfoDTO> listPostInfo = new ArrayList<>();
            for (com.lpb.esb.service.common.model.request.settle.PostInfoDTO post : settleBillDTO.getListPostInfo()) {
                PostInfoDTO postInfoDTO = PostInfoDTO.builder()
                    .sourceAcc(post.getSourceAcc())
                    .acNo(post.getAcNo())
                    .branchCode(post.getBranchCode())
                    .ccy(post.getCcy())
                    .fcyAmount(post.getFcyAmount())
                    .lcyAmount(post.getLcyAmount())
                    .drcrInd(post.getDrcrInd())
                    .amountTag(post.getAmountTag())
                    .bankCode(post.getBankCode())
                    .bankName(post.getBankName())
                    .makerId(post.getMakerid())
                    .checkerId(post.getCheckerid())
                    .infoSourceAcc(post.getInfoSourceAcc())
                    .infoAcNo(post.getInfoAcNo())
                    .postDesc(post.getPostDesc())
                    .postRefNo(post.getPostRefNo())
                    .build();
                listPostInfo.add(postInfoDTO);
            }
            requestSettleBillDTO.setPostInfo(listPostInfo);
        }
        if (settleBillDTO.getListBillInfo() != null) {
            List<BillDTO> listBill = new ArrayList<>();
            for (com.lpb.esb.service.common.model.request.infocustomerbill.BillDTO bill : settleBillDTO.getListBillInfo()) {
                BillDTO billDTO = BillDTO.builder()
                    .billCode(bill.getBillCode())
                    .billDesc(bill.getBillDesc())
                    .billAmount(bill.getBillAmount())
                    .amtUnit(bill.getAmtUnit())
                    .settledAmount(bill.getSettledAmount())
                    .otherInfo(bill.getOtherInfo())
                    .billType(bill.getBillType())
                    .billStatus(bill.getBillStatus())
                    .billId(bill.getBillId())
                    .paymentMethod(bill.getPaymentMethod())
                    .build();
                listBill.add(billDTO);
            }
            requestSettleBillDTO.setBillInfo(listBill);
        }
        baseRequestDTO.setHeader(headerInfoDTO);
        bodyInfoDTO.setData(requestSettleBillDTO);
        baseRequestDTO.setBody(bodyInfoDTO);
        return baseRequestDTO;
    }

    private BaseRequestDTO mapObjectGetSettleBill(DataSettleDTO REQ, String appId) {
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
            .transactionId(appId)
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
            CustomerDTO customerInfo =
                CustomerDTO.builder()
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

    @Override
    public ResponseModel excuteRevertService(DataSettleDTO REQ) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        DataSettleDTO RES = new DataSettleDTO();

        String appMsgId = "";
        HeaderDTO header = REQ.getHeader();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            log.info(header.getMsgid() + " Request RevertBill: " + objectMapper.writeValueAsString(REQ));
//            Get thông tin kết nối
            List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB, REQ.getBody().getSettleBill().getService().getServiceId(),
                REQ.getBody().getSettleBill().getService().getProductCode(), REQ.getHeader().getOperation());
            if (list.size() == 0) {
                log.info(header.getMsgid() + " Error RQ RevertBill: 493 - Dịch vụ Nhà cung cấp không hợp lệ");
                lpbResCode.setErrorCode("493");
                lpbResCode.setErrorDesc("Dịch vụ Nhà cung cấp không hợp lệ");
                responseModel = ResponseModel.builder().resCode(lpbResCode).data(REQ).build();
                return responseModel;
            }
            ServiceInfo serviceInfo = list.get(0);
            // Get APPMSG_ID làm tran_ID
            appMsgId = getTranId.getTranId();
            if (appMsgId == null || appMsgId.length() < 1) {
                log.info(header.getMsgid() + " Error RQ RevertBill: 285 - Không thể thêm vào DB do lỗi: " +
                    "Select SEQ_MICROGATEWAY.nextval AS TRAN_ID from dual");
                lpbResCode.setErrorCode("285");
                lpbResCode.setErrorDesc("Không thể thêm vào DB do lỗi: Select SEQ_MICROGATEWAY.nextval AS TRAN_ID from dual");
                responseModel = ResponseModel.builder().resCode(lpbResCode).data(REQ).build();
                return responseModel;
            }
            REQ.getBody().getSettleBill().setTransactionId(appMsgId);

            // Init RevertBill
            responseModel = TransactionService.initRevertBill(restTemplateLB, mapObjectInit(REQ));
            if (!responseModel.getResCode().getErrorCode().equals("00")) {
                log.info(appMsgId + "-" + header.getMsgid() + " Error RQ RevertBill: 285 - Không thể thêm vào DB do lỗi: " +
                    "TransactionService.initRevertBill(restTemplateLB, baseRequestDTO)");
                lpbResCode.setErrorCode("285");
                lpbResCode.setErrorDesc("Không thể thêm vào DB do lỗi: TransactionService.initRevertBill(restTemplateLB, baseRequestDTO)");
                lpbResCode.setRefCode(responseModel.getResCode().getErrorCode());
                lpbResCode.setRefDesc(responseModel.getResCode().getErrorDesc());
                responseModel = ResponseModel.builder().resCode(lpbResCode).data(REQ).build();
                return responseModel;
            }
                //get RevertBill
                TransactionService.getRevertBill(restTemplateLB, mapObjectGetSettleBill(REQ, appMsgId));
                //xử lý gọi đi đối tác
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                header.setDestination(serviceInfo.getConnectorURL());
                REQ.setHeader(header);
                log.info(appMsgId + "-" + header.getMsgid() + " Request call partner: " + objectMapper.writeValueAsString(REQ));
                HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(REQ), headers);
                log.info(appMsgId + "-" + header.getMsgid() + " Request call partner: " + entity);

                String response = restTemplateLB.exchange(serviceInfo.getUrlApi(), HttpMethod.POST, entity, String.class).getBody();

                log.info(appMsgId + "-" + header.getMsgid() + " Response call partner: " + response);
                JSONObject jsonResponse = new JSONObject(response);
                JSONObject jsonResCode = jsonResponse.getJSONObject("resCode");
                JSONObject jsonData = jsonResponse.getJSONObject("data");
                RES = objectMapper.readValue(jsonData.toString(), DataSettleDTO.class);
                lpbResCode = objectMapper.readValue(jsonResCode.toString(), LpbResCode.class);
                log.info(appMsgId + "-" + header.getMsgid() + " Response: " + objectMapper.writeValueAsString(RES));

                //log giao dịch vào bảng esb_trans_log
                logTrans.logTrans(RES, REQ, appMsgId);
                //update trans
                updateTrans.updateTrans(RES, REQ, appMsgId, lpbResCode);

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
                logTrans.logTrans(RES, REQ, appMsgId);
                //update trans
                updateTrans.updateTrans(RES, REQ, appMsgId, lpbResCode);

                //set servicelog = null
//                RES.getBody().getSettleBill().setServiceLog(null);
            } catch (Exception ex) {
                log.info(appMsgId + "-" + header.getMsgid() + " Exception SettleBill: " + e);
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
        } catch (ResourceAccessException e) {
            log.info(appMsgId + "-" + header.getMsgid() + " ResourceAccessException SettleBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(appMsgId + "-" + header.getMsgid() + " Exception SettleBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel = ResponseModel.builder().resCode(lpbResCode).data(RES).build();
        log.info(appMsgId + "-" + header.getMsgid() + " Response" + responseModel);
        return responseModel;
    }
}
