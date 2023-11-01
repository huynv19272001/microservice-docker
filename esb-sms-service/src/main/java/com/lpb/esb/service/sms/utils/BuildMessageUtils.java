package com.lpb.esb.service.sms.utils;

import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.sms.model.config.SmsFileConfig;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by tudv1 on 2022-04-28
 */
@Component
@Log4j2
public class BuildMessageUtils {
    @Autowired
    SmsFileConfig smsFileConfig;

    @Autowired
    VnpUtils vnpUtils;
    public String buildMsgSmsRequest(EsbSmsRequest esbSmsRequest, ServiceInfo serviceInfo) {
        final String path = smsFileConfig.getFilePrefix() + smsFileConfig.getEsbXmlBodyRequest();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase
            , esbSmsRequest.getEsbHeader().getMsgId()
            , serviceInfo.getConnectorURL()
            , esbSmsRequest.getEsbHeader().getServiceId()
            , esbSmsRequest.getEsbHeader().getProductCode()
            , esbSmsRequest.getEsbBody().getMobileNumber()
            , esbSmsRequest.getEsbBody().getContent()
            , esbSmsRequest.getEsbBody().getServiceLog() + ""
            , esbSmsRequest.getEsbBody().getCategory()
        );
    }

    public String buildMsgVnpSmsRequest(EsbSmsRequest esbSmsRequest, ServiceInfo serviceInfo) {
        final String path = smsFileConfig.getFilePrefix() + smsFileConfig.getVnpXmlBodyRequest();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase
            , esbSmsRequest.getEsbBody().getMobileNumber()
            , serviceInfo.getUdf3()
            , serviceInfo.getUdf6()
            , esbSmsRequest.getEsbBody().getContent()
            , "0"
            , "0"
            , "SMS"
            , vnpUtils.getTimeLong(new Date())
            , serviceInfo.getUdf7()
            , serviceInfo.getUdf8()
        );
    }

}
