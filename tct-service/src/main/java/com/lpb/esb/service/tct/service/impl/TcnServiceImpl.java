package com.lpb.esb.service.tct.service.impl;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.tct.model.request.RequestData;
import com.lpb.esb.service.tct.service.TcnService;
import com.lpb.esb.service.tct.util.BuildMessageUtils;
import com.lpb.esb.service.tct.util.LogicUtils;
import com.lpb.esb.service.tct.util.RequestUtils;
import com.lpb.esb.service.tct.util.config.ModuleConfig;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by tudv1 on 2022-02-28
 */
@Service
@Log4j2
public class TcnServiceImpl implements TcnService {
    @Autowired
    RestTemplate restTemplateLB;
    @Autowired
    BuildMessageUtils buildMessageUtils;
    @Autowired
    LogicUtils logicUtils;
    @Autowired
    RequestUtils requestUtils;

    @Override
    public ResponseModel search(RequestData requestData) {
        List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB
            , requestData.getEsbHeader().getServiceId()
            , requestData.getEsbHeader().getProductCode()
            , requestData.getEsbHeader().getHasRole()
        );

        if (list.size() == 0) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service not found")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(requestData)
                .build();
            return responseModel;
        }

        ServiceInfo serviceInfo = list.get(0);

        // Build message XML
        String header = buildMessageUtils.buildTctHeader(requestData.getTctHeader());
        String body = buildMessageUtils.buildBodyTCN(requestData);
        String dataRequest = buildMessageUtils.buildMessageRequest(header, body);

        // Make request to T2B
        ExecuteModel<String> response = requestUtils.callAPI(dataRequest, serviceInfo);
        log.info("Res: [{}]\t{}", response.getStatusCode(), response.getData());

        if (response.getStatusCode().intValue() == 200) {
            //        String response = "";
            String responseXml = response.getData();

            // Build message response
            JSONObject jsonObject = logicUtils.parseDataFromXml(responseXml);
            try {
                jsonObject.getJSONObject("DATA").remove("SECURITY");
            } catch (Exception e) {
            }

            logicUtils.convertData(jsonObject, ModuleConfig.TCN);

            jsonObject.put("TCT_DATA", jsonObject.remove("DATA"));

            JSONObject out = logicUtils.recursiveJsonKeyConverterToLower(jsonObject);

            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Success")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(out.toMap())
                .build();
            return responseModel;
        } else {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Failed")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(response.getData())
                .build();
            return responseModel;
        }
    }
}
