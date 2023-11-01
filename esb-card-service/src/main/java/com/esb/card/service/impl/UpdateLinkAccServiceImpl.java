package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.CardREQDTO;
import com.esb.card.dto.CardRESDTO;
import com.esb.card.dto.ServiceInfoDTO;
import com.esb.card.dto.updatelinkacc.UpdateLinkAccREQDTO;
import com.esb.card.dto.updatelinkacc.UpdateLinkAccRESDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.service.IEsbService;
import com.esb.card.service.UpdateLinkAccService;
import com.esb.card.utils.BuildMessageUtils;
import com.esb.card.utils.RSAUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
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
public class UpdateLinkAccServiceImpl implements UpdateLinkAccService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEsbService esbService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseModel updateLinkAcc(UpdateLinkAccREQDTO updateLinkAccREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(updateLinkAccREQDTO.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(updateLinkAccREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoUpdateLinkAcc();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = initUpdateLinkAccRequest(serviceInfo, updateLinkAccREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(updateLinkAccREQDTO.getMsgId() + " Request UpdateLinkAcc: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(updateLinkAccREQDTO.getMsgId() + " Response UpdateLinkAcc: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(convertUpdateLinkAccRESDTO(cardResponse, esbCardCoreUserInfo, updateLinkAccREQDTO));
            } else {
                lpbResCode.setErrorCode(ErrorMessageEng.OTHER_ERROR.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.OTHER_ERROR.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(updateLinkAccREQDTO.getMsgId() + " Response UpdateLinkAcc: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(updateLinkAccREQDTO.getMsgId() + " Exception UpdateLinkAcc: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    private List<ServiceInfoDTO> initServiceInfoUpdateLinkAcc() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.UPDATE_LINK_ACC, Constant.HAS_ROLE);
        return serviceInfo;
    }

    public static CardREQDTO initUpdateLinkAccRequest(List<ServiceInfoDTO> serviceInfo, UpdateLinkAccREQDTO updateLinkAccREQDTO,
                                                      EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgUpdateLinkAcc(updateLinkAccREQDTO);
        return BuildMessageUtils.initCardReq(serviceInfo, esbCardCoreUserInfo, updateLinkAccREQDTO.getMsgId(), msg);
    }

    public static String buildMsgUpdateLinkAcc(UpdateLinkAccREQDTO updateLinkAccREQDTO) {
        try {
            UpdateLinkAccREQDTO buildXml = UpdateLinkAccREQDTO.builder()
                .cardNumber(updateLinkAccREQDTO.getCardNumber())
                .action(updateLinkAccREQDTO.getAction())
                .cif(updateLinkAccREQDTO.getCif())
                .basicCardFlag(updateLinkAccREQDTO.getBasicCardFlag())
                .listChangeAccountREQDTO(updateLinkAccREQDTO.getListChangeAccountREQDTO())
                .build();

            JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setDefaultUseWrapper(false);
            ObjectMapper objectMapper = new XmlMapper(xmlModule);
            String xml = objectMapper.writeValueAsString(buildXml);
            return xml;
        } catch (Exception e) {
            log.info("Exception buildMsgUpdateLinkAcc: " + e);
            return null;
        }
    }

    public static UpdateLinkAccRESDTO convertUpdateLinkAccRESDTO(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                                                 UpdateLinkAccREQDTO updateLinkAccREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(updateLinkAccREQDTO.getMsgId() + " UpdateLinkAccREQDTO: " + "RES " + xmlResponse);
        UpdateLinkAccRESDTO updateLinkAccRESDTO = xmlMapper.readValue(xmlResponse, UpdateLinkAccRESDTO.class);

        return updateLinkAccRESDTO;
    }
}
