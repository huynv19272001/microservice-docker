package com.lpb.esb.service.config.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.request.transaction.RequestSettleBillDTO;
import com.lpb.esb.service.common.model.request.transaction.RequestTransactionDTO;
import com.lpb.esb.service.common.model.request.transaction.SettleBillingDTO;
import com.lpb.esb.service.common.model.request.transaction.UpdateSettleBillDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.constant.Constant;
import com.lpb.esb.service.config.repository.SettleBillDAO;
import com.lpb.esb.service.config.repository.SettleBillRepository;
import com.lpb.esb.service.config.service.EsbTransactionBillingService;
import com.lpb.esb.service.config.service.SettleBillService;
import com.lpb.esb.service.config.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class SettleBillServiceImpl implements SettleBillService {

    @Autowired
    SettleBillDAO settleBillDAO;

    @Autowired
    SettleBillRepository settleBillRepository;

    @Autowired
    EsbTransactionBillingService esbTransactionBillingService;

    @Override
    public ResponseModel initSettleBill(BaseRequestDTO baseRequestDTO) {

        log.info("MSGREQ: " + baseRequestDTO);
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RequestSettleBillDTO request = objectMapper.readValue
                (objectMapper.writeValueAsString(baseRequestDTO.getBody().getData()), RequestSettleBillDTO.class);
            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId()
                + " Request InitSettleBill " + objectMapper.writeValueAsString(baseRequestDTO));

            SettleBillRepository.ServiceType serviceType = settleBillRepository.loadServiceTypeByService
                (request.getServiceInfo().getServiceId());
            if (serviceType != null) {
                String xmlTypeService = "";
                switch (serviceType.getServiceType()) {
                    case Constant.TRANSFER:
                        log.info("RequestSettleBills LIST TRANSFER INPUT: " + request.getPostInfo().size());
                        xmlTypeService = Utils.genTransferXML(request.getServiceInfo(), request.getPostInfo(),
                            baseRequestDTO.getHeader().getMsgId());
                        log.info("RequestSettleBills TRANSFER: " + xmlTypeService);
                        break;
                    default:
                        log.info("RequestSettleBills LIST BILL INPUT: " + request.getBillInfo().size());
                        xmlTypeService = Utils.genBillsXML(request.getServiceInfo(), request.getBillInfo(),
                            baseRequestDTO.getHeader().getMsgId());
                        log.info("RequestSettleBills BILLING: " + xmlTypeService);
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
                    Constant.ESB_REQUEST_TXN,
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
    public ResponseModel getSettleBill(BaseRequestDTO baseRequestDTO) {
        log.info("MSGREQ: " + baseRequestDTO);
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RequestTransactionDTO request = objectMapper.readValue
                (objectMapper.writeValueAsString(baseRequestDTO.getBody().getData()), RequestTransactionDTO.class);
            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " Request GetSettleBill "
                + objectMapper.writeValueAsString(baseRequestDTO));

            SettleBillRepository.ServiceType serviceType = settleBillRepository.loadServiceType(request.getTransactionId());
            if (serviceType != null) {
                log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " ServiceType GetSettleBill "
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
                            settleBillRepository.genSqlLoadDetailBilling(request.getTransactionId());
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
            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " Response GetSettleBill "
                + objectMapper.writeValueAsString(responseModel));
        } catch (Exception e) {
            e.printStackTrace();
            log.info(baseRequestDTO.getHeader().getMsgId() + " Exception GetSettleBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public ResponseModel updateSettleBill(BaseRequestDTO baseRequestDTO) {
        log.info("MSGREQ: " + baseRequestDTO);
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UpdateSettleBillDTO request = objectMapper.readValue
                (objectMapper.writeValueAsString(baseRequestDTO.getBody().getData()), UpdateSettleBillDTO.class);
            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " Request UpdateSettleBill "
                + objectMapper.writeValueAsString(baseRequestDTO));

            SettleBillRepository.ServiceType serviceType = settleBillRepository.loadServiceType(request.getTransactionId());

            if (serviceType != null) {
                log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " ServiceType UpdateSettleBill "
                    + serviceType.getServiceType());
                responseModel = settleBillDAO.updateSettleBill(request, Utils.buildServiceInfo(request.getServiceInfo()),
                    serviceType.getServiceType(), baseRequestDTO);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
                responseModel.setResCode(lpbResCode);
            }
            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " Response UpdateSettleBill "
                + objectMapper.writeValueAsString(responseModel));
        } catch (Exception e) {
            log.info(baseRequestDTO.getHeader().getMsgId() + " Exception UpdateSettleBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    @Override
    public ResponseModel billingLog(BaseRequestDTO baseRequestDTO) {
        log.info("MSGREQ: " + baseRequestDTO);
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + " Request BillingLog "
                + objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = settleBillDAO.billingLog(baseRequestDTO);

            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + " Response BillingLog "
                + objectMapper.writeValueAsString(responseModel));
        } catch (Exception e) {
            log.info(baseRequestDTO.getHeader().getMsgId() + " Exception BillingLog: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

}
