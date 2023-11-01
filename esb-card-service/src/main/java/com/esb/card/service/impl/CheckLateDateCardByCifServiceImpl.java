package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.CardREQDTO;
import com.esb.card.dto.CardRESDTO;
import com.esb.card.dto.ServiceInfoDTO;
import com.esb.card.dto.checklatedatcard.CheckLateDateCardByCifREQDTO;
import com.esb.card.dto.checklatedatcard.CheckLateDateCardByCifRESDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.service.CheckLateDateCardByCifService;
import com.esb.card.service.IEsbService;
import com.esb.card.utils.BuildMessageUtils;
import com.esb.card.utils.RSAUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.StringUtils;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.common.utils.code.ErrorMessageEng;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
public class CheckLateDateCardByCifServiceImpl implements CheckLateDateCardByCifService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEsbService esbService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseModel checkLateDateCardByCif(CheckLateDateCardByCifREQDTO checkLateDateCardByCifREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(checkLateDateCardByCifREQDTO.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(checkLateDateCardByCifREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoCheckLateDateCardByCif();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = initServiceInfoCheckLateDateCardByCifRequest(serviceInfo, checkLateDateCardByCifREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(checkLateDateCardByCifREQDTO.getMsgId() + " Request CheckLateDateCardByCif: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(checkLateDateCardByCifREQDTO.getMsgId() + " Response CheckLateDateCardByCif: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(convertCheckLateDateCardByCifRESDTO(cardResponse, esbCardCoreUserInfo, checkLateDateCardByCifREQDTO));
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(checkLateDateCardByCifREQDTO.getMsgId() + " Response CheckLateDateCardByCif: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            log.info(checkLateDateCardByCifREQDTO.getMsgId() + " Exception CheckLateDateCardByCif: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    private List<ServiceInfoDTO> initServiceInfoCheckLateDateCardByCif() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.CHECK_LATE_DATE_CARD_BY_CIF, Constant.HAS_ROLE);
        return serviceInfo;
    }

    public static CardREQDTO initServiceInfoCheckLateDateCardByCifRequest(List<ServiceInfoDTO> serviceInfo, CheckLateDateCardByCifREQDTO checkLateDateCardByCifREQDTO,
                                                             EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgCheckLateDateCardByCif(checkLateDateCardByCifREQDTO);
        return BuildMessageUtils.initCardReq(serviceInfo, esbCardCoreUserInfo, checkLateDateCardByCifREQDTO.getMsgId(), msg);
    }

    public static String buildMsgCheckLateDateCardByCif(CheckLateDateCardByCifREQDTO checkLateDateCardByCifREQDTO) {
        StringBuilder bu = new StringBuilder();
        bu.append("<CheckLateDateCardByCifRequest>");
        if (!StringUtils.isNullOrBlank(checkLateDateCardByCifREQDTO.getCif())) {
            bu.append("<Cif>");
            bu.append(checkLateDateCardByCifREQDTO.getCif());
            bu.append("</Cif>");
        } else {
            bu.append("Cif");
        }
        bu.append("</CheckLateDateCardByCifRequest>");
        return bu.toString();
    }

    public static CheckLateDateCardByCifRESDTO convertCheckLateDateCardByCifRESDTO(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                                                                   CheckLateDateCardByCifREQDTO checkLateDateCardByCifREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(checkLateDateCardByCifREQDTO.getMsgId() + " CheckLateDateCardByCifRESDTO: " + "RES " + xmlResponse);
        CheckLateDateCardByCifRESDTO checkLateDateCardByCifRESDTO = xmlMapper.readValue(xmlResponse, CheckLateDateCardByCifRESDTO.class);

        return checkLateDateCardByCifRESDTO;
    }
}
