package com.lpb.esb.service.sms.service.impl;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.CacheUtils;
import com.lpb.esb.service.common.utils.RequestUtils;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import com.lpb.esb.service.sms.process.SmsSendProcess;
import com.lpb.esb.service.sms.process.impl.SmsSendFtelProcessImpl;
import com.lpb.esb.service.sms.process.impl.SmsSendVNPProcessImpl;
import com.lpb.esb.service.sms.service.SmsSendService;
import com.lpb.esb.service.sms.utils.BuildMessageUtils;
import com.lpb.esb.service.sms.utils.FtelUtils;
import com.lpb.esb.service.sms.utils.LogicUtils;
import com.lpb.esb.service.sms.utils.VnpUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by tudv1 on 2021-08-13
 */
@Service
@Log4j2
public class SmsSendServiceImpl implements SmsSendService {
    @Value("${spring.application.name}")
    String module;
    @Value("${spring.profiles.active:local}")
    String profile;

    @Autowired
    @Qualifier("smsTelegramProcess")
    SmsSendProcess smsTelegramProcess;
    @Autowired
    BuildMessageUtils buildMessageUtils;
    @Autowired
    SmsSendFtelProcessImpl smsSendFtelProcess;
    @Autowired
    FtelUtils ftelUtils;
    @Autowired
    LogicUtils logicUtils;
    @Autowired
    RestTemplate restTemplateLoadBalancer;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    SmsSendVNPProcessImpl smsSendVNPProcess;
    @Autowired
    VnpUtils vnpUtils;

    @Override
    public ExecuteModel sendTelegram(String message, String chatId) {
        return smsTelegramProcess.sendMessage(message, chatId);
    }

    @Override
    public ResponseModel sendSmsEsb(EsbSmsRequest esbSmsRequest) {
        List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLoadBalancer
            , esbSmsRequest.getEsbHeader().getServiceId()
            , esbSmsRequest.getEsbHeader().getProductCode()
            , esbSmsRequest.getEsbHeader().getHasRole()
        );

        if (list.size() == 0) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service not found")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(esbSmsRequest)
                .build();
            return responseModel;
        }

        ServiceInfo serviceInfo = list.get(0);

        String xmlReq = buildMessageUtils.buildMsgSmsRequest(esbSmsRequest, serviceInfo);

        ResponseEntity<String> resHttp = RequestUtils.executePostReq(restTemplate, serviceInfo.getUrlApi(), xmlReq);
        log.info(resHttp.toString());


        return null;
    }

    @Override
    public ResponseModel sendSmsFtel(EsbSmsRequest esbSmsRequest) {

        // Get service info
        List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLoadBalancer
            , esbSmsRequest.getEsbHeader().getServiceId()
            , esbSmsRequest.getEsbHeader().getProductCode()
            , esbSmsRequest.getEsbHeader().getHasRole()
        );
        if (list.size() == 0) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service not found")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(esbSmsRequest)
                .build();
            return responseModel;
        }
        ServiceInfo serviceInfo = list.get(0);

        // Get token
        String keyTokenCache = logicUtils.buildFtelKey(esbSmsRequest);
        String tokenCache = CacheUtils.getCacheValue(restTemplateLoadBalancer, this.profile + "_" + this.module, keyTokenCache);
        if (tokenCache == null) {
            ExecuteModel<String> exeLogin = smsSendFtelProcess.loginFtel(serviceInfo, esbSmsRequest);
            if (exeLogin.getStatusCode().intValue() != 200) {
                ResponseModel responseModel = ftelUtils.parseFtelError(exeLogin, esbSmsRequest);
                return responseModel;
            }

            tokenCache = ftelUtils.getTokenLoginFtel(exeLogin.getMessage());
            long ttl = ftelUtils.getTtlTokenFtel(exeLogin.getMessage());
            CacheUtils.putDataToCache(restTemplateLoadBalancer, this.profile + "_" + this.module, keyTokenCache, tokenCache, ttl - 3 * 60 * 60);
        }


        // Push sms
        ExecuteModel<String> exePushBranchName = smsSendFtelProcess.sendFtelSms(serviceInfo, esbSmsRequest, tokenCache);
//        if (exePushBranchName.getStatusCode().intValue() != 200) {
//            ResponseModel responseModel = ftelUtils.parseFtelError(exePushBranchName);
//            return responseModel;
//        }
//        ResponseModel responseModel = ftelUtils.parseFtelPushSuccess(exePushBranchName.getMessage());
        ResponseModel responseModel = ftelUtils.parseFtelError(exePushBranchName, esbSmsRequest);

        return responseModel;
    }

    @Override
    public ResponseModel sendSmsVnpay(EsbSmsRequest esbSmsRequest) {

        // Get service info
        List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLoadBalancer
            , esbSmsRequest.getEsbHeader().getServiceId()
            , esbSmsRequest.getEsbHeader().getProductCode()
            , esbSmsRequest.getEsbHeader().getHasRole()
        );
        if (list.size() == 0) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service not found")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(esbSmsRequest)
                .build();
            return responseModel;
        }
        ServiceInfo serviceInfo = list.get(0);

        // Push sms
        ExecuteModel<String> exePushBranchName = smsSendVNPProcess.sendVNPSms(serviceInfo, esbSmsRequest);
//        if (exePushBranchName.getStatusCode().intValue() != 200) {
        ResponseModel responseModel = vnpUtils.parseVnpError(exePushBranchName, esbSmsRequest);
//            return responseModel;
//        }
//        ResponseModel responseModel = vnpUtils.parseVnpPushSuccess(exePushBranchName.getMessage());

        return responseModel;
    }

    @Override
    public ResponseModel sendSmsVnpXml(EsbSmsRequest esbSmsRequest) {
        String url = "https://svr-vnp.vnpaytest.vn/LPBSMS/MTSend.asmx";
        List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLoadBalancer
            , esbSmsRequest.getEsbHeader().getServiceId()
            , esbSmsRequest.getEsbHeader().getProductCode()
            , esbSmsRequest.getEsbHeader().getHasRole()
        );

        if (list.size() == 0) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service not found")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(esbSmsRequest)
                .build();
            return responseModel;
        }

        ServiceInfo serviceInfo = list.get(0);

        String xmlReq = buildMessageUtils.buildMsgVnpSmsRequest(esbSmsRequest, serviceInfo);

        ResponseModel responseModel = RequestUtils.executePostXmlReq(restTemplate, url, xmlReq);

        return responseModel;

    }
}
