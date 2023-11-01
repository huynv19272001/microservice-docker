package com.lpb.esb.service.config.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.request.transaction.RequestSettleBillDTO;
import com.lpb.esb.service.common.model.request.transaction.RequestTransactionDTO;
import com.lpb.esb.service.common.model.request.transaction.SettleBillingDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.constant.Constant;
import com.lpb.esb.service.config.repository.SettleBillDAO;
import com.lpb.esb.service.config.repository.SettleBillRepository;
import com.lpb.esb.service.config.service.EsbTransactionBillingService;
import com.lpb.esb.service.config.service.RevertBillService;
import com.lpb.esb.service.config.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class RevertBillServiceImpl implements RevertBillService {

    @Autowired
    SettleBillDAO settleBillDAO;

    @Autowired
    SettleBillRepository settleBillRepository;

    @Autowired
    EsbTransactionBillingService esbTransactionBillingService;

    @Override
    public ResponseModel initRevertBill(BaseRequestDTO baseRequestDTO) {

        log.info("MSGREQ: " + baseRequestDTO);
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RequestSettleBillDTO request = objectMapper.readValue
                (objectMapper.writeValueAsString(baseRequestDTO.getBody().getData()), RequestSettleBillDTO.class);
            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId()
                + " Request InitRevertBill " + objectMapper.writeValueAsString(baseRequestDTO));

            SettleBillRepository.ServiceType serviceType = settleBillRepository.loadServiceTypeByService
                (request.getServiceInfo().getServiceId());
            if (serviceType != null) {
                String xmlTypeService = "";
                switch (serviceType.getServiceType()) {
                    case Constant.BILLING:
                        log.info("RequestRevertBills LIST BILL INPUT: " + request.getBillInfo().size());
                        xmlTypeService = Utils.genBillsXML(request.getServiceInfo(), request.getBillInfo(),
                            baseRequestDTO.getHeader().getMsgId());
                        log.info("RequestRevertBills BILLING: " + xmlTypeService);
                        break;
                    case Constant.TRANSFER:
                        log.info("RequestRevertBills LIST TRANSFER INPUT: " + request.getBillInfo().size());
                        xmlTypeService = Utils.genTransferRevertXML(request.getServiceInfo(), request.getBillInfo(),
                            baseRequestDTO.getHeader().getMsgId());
                        log.info("RequestRevertBills TRANSFER: " + xmlTypeService);
                        break;
                    default:
                        log.info("RequestRevertBills LIST OTHER INPUT: " + request.getBillInfo().size());
                        xmlTypeService = Utils.genOtherRevertXML(request.getServiceInfo(), request.getPostInfo(),
                            baseRequestDTO.getHeader().getMsgId());
                        log.info("RequestRevertBills OTHER: " + xmlTypeService);
                        break;
                }
                responseModel = settleBillDAO.switchingEsb(baseRequestDTO.getHeader().getUserId(),
                    request.getTrnBrn(),
                    request.getTrnDesc(),
                    request.getTransactionId(),
                    Utils.buildCustomerNo(request.getCustomerNo(), baseRequestDTO.getHeader().getMsgId()),
                    request.getConfirmTrn(),
                    Utils.createXmlPartner(request.getPartnerInfo(), baseRequestDTO.getHeader().getMsgId()),
                    xmlTypeService,
                    Constant.ESB_REVERT_TXN,
                    baseRequestDTO);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.SERVICE_NOT_EXIST.label);
                lpbResCode.setErrorDesc(ErrorMessage.SERVICE_NOT_EXIST.description);
                responseModel.setResCode(lpbResCode);
            }
            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId()
                + " Response InitSettleBill " + objectMapper.writeValueAsString(responseModel));
        } catch (Exception e) {
            log.info(baseRequestDTO.getHeader().getMsgId() + " Exception InitSettleBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        responseModel.setData(baseRequestDTO.getBody().getData());

        return responseModel;
    }

    @Override
    public ResponseModel getRevertBill(BaseRequestDTO baseRequestDTO) {
        log.info("MSGREQ: " + baseRequestDTO);
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RequestTransactionDTO request = objectMapper.readValue
                (objectMapper.writeValueAsString(baseRequestDTO.getBody().getData()), RequestTransactionDTO.class);
            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " Request GetRevertBill "
                + objectMapper.writeValueAsString(baseRequestDTO));

            SettleBillRepository.ServiceType serviceType = settleBillRepository.loadServiceType(request.getTransactionId());
            if (serviceType != null) {
                log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " ServiceType GetRevertBill "
                    + serviceType.getServiceType());
                switch (serviceType.getServiceType()) {
                    case Constant.TRANSFER:
                        List<SettleBillRepository.TransactionSettleBillTransfer> listTransfer =
                            settleBillRepository.genSqlLoadDetailTransfer(request.getTransactionId());
                        if (listTransfer != null && !listTransfer.isEmpty()) {
                            responseModel.setData(Utils.convertSettleBillTransfer(listTransfer));
                            lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                            lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                        } else {
                            lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                            lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
                        }
                        break;
                    default:
                        List<SettleBillRepository.TransactionSettleBilling> listBilling =
                            settleBillRepository.genSqlLoadDetailBillingRevert(request.getTransactionId());
                        if (listBilling != null && !listBilling.isEmpty()) {
                            List<SettleBillingDTO> listSettleBillingDTO = Utils.convertSettleBilling(listBilling);
                            responseModel.setData(listSettleBillingDTO);
                            lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                            lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                            //update lại status transaction tránh gọi 2 lần
                            esbTransactionBillingService.updateEsbTransactionBilling(listSettleBillingDTO.get(0).getTransactionId(),
                                listSettleBillingDTO.get(0).getServiceInfo().getServiceId());
                        } else {
                            lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                            lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
                        }
                        break;
                }
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " Response GetRevertBill "
                + objectMapper.writeValueAsString(responseModel));
        } catch (Exception e) {
            e.printStackTrace();
            log.info(baseRequestDTO.getHeader().getMsgId() + " Exception GetRevertBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }
}
