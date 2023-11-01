package com.lpb.esb.service.config.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.request.transaction.RequestTransactionDTO;
import com.lpb.esb.service.common.model.request.transaction.UpdateTransactionDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.repository.TransactionPostRepository;
import com.lpb.esb.service.config.repository.TransactionDAO;
import com.lpb.esb.service.config.service.TransactionService;
import com.lpb.esb.service.config.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    TransactionPostRepository esbTransactionPostRepository;

    @Override
    public ResponseModel initTransaction(BaseRequestDTO baseRequestDTO) {
        log.info("MSGREQ: " + baseRequestDTO);
        ResponseModel responseModel = ResponseModel.builder().build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RequestTransactionDTO request = objectMapper.readValue
                (objectMapper.writeValueAsString(baseRequestDTO.getBody().getData()), RequestTransactionDTO.class);
            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() +
                " Request InitTransaction " + objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = transactionDAO.initTransaction(request.getUserId(),
                request.getTrnBrn(),
                request.getTrnDesc(),
                request.getTransactionId(),
                Utils.createXmlCustomer(request.getCustomerInfo(), baseRequestDTO.getHeader().getMsgId()),
                Utils.createXmlPartner(request.getPartnerInfo(), baseRequestDTO.getHeader().getMsgId()),
                Utils.createXmlServiceDTO(request.getServiceInfo(), baseRequestDTO.getHeader().getMsgId()),
                Utils.createXmlPostInfo(request.getPostInfo(), baseRequestDTO.getHeader().getMsgId()),
                baseRequestDTO);

            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() +
                " Response InitTransaction " + objectMapper.writeValueAsString(responseModel));
        } catch (Exception e) {
            e.printStackTrace();
            log.info(baseRequestDTO.getHeader().getMsgId() + " Exception: " + e);
            LpbResCode lpbResCode = LpbResCode.builder().build();
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        responseModel.setData(baseRequestDTO.getBody().getData());
        return responseModel;
    }

    @Override
    public ResponseModel getTransactionPost(BaseRequestDTO baseRequestDTO) {
        log.info("MSGREQ: " + baseRequestDTO);
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RequestTransactionDTO request = objectMapper.readValue
                (objectMapper.writeValueAsString(baseRequestDTO.getBody().getData()), RequestTransactionDTO.class);

            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " Request LoadTransactionPost "
                + objectMapper.writeValueAsString(baseRequestDTO));

            List<TransactionPostRepository.TransactionPost> data =
                esbTransactionPostRepository.getTransactionPost(request.getTransactionId());
            if (data != null && !data.isEmpty()) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
            responseModel.setData(data);
            responseModel.setResCode(lpbResCode);

            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " Response LoadTransactionPost "
                + objectMapper.writeValueAsString(responseModel));
        } catch (Exception e) {
            log.info(baseRequestDTO.getHeader().getMsgId() + " Exception: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }

    @Override
    public ResponseModel updateTransaction(BaseRequestDTO baseRequestDTO) {
        log.info("MSGREQ: " + baseRequestDTO);
        ResponseModel responseModel = ResponseModel.builder().build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UpdateTransactionDTO request = objectMapper.readValue
                (objectMapper.writeValueAsString(baseRequestDTO.getBody().getData()), UpdateTransactionDTO.class);

            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " Request UpdateTransaction "
                + objectMapper.writeValueAsString(baseRequestDTO));

            responseModel = transactionDAO.updateTransaction(request, baseRequestDTO.getHeader().getMsgId());

            log.info(baseRequestDTO.getHeader().getMsgId() + "-" + request.getTransactionId() + " Response UpdateTransaction "
                + objectMapper.writeValueAsString(responseModel));
        } catch (Exception e) {
            log.info(baseRequestDTO.getHeader().getMsgId() + " Exception: " + e);
            LpbResCode lpbResCode = LpbResCode.builder().build();
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
        }
        return responseModel;
    }
}
