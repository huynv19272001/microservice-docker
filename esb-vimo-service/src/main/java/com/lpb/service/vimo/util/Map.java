package com.lpb.service.vimo.util;

import com.lpb.service.vimo.model.ESBSettleBillRequest;
import com.lpb.service.vimo.model.ESBSettleBillResponse;
import com.lpb.service.vimo.model.ESBRequest;
import com.lpb.service.vimo.model.ESBResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Map {

    private static final Logger logger = LogManager.getLogger(Map.class);

    public static ESBResponse mapResponse(ESBRequest request) {
        logger.info("<--- resquest ESBRequest: " + request.toString());
        ESBResponse response = new ESBResponse();
        ESBResponse.DATA data = new ESBResponse.DATA();
        ESBResponse.Body body = new ESBResponse.Body();
        ESBRequest.Header header = request.getHeader();
        ESBRequest.Service service = request.getBody().getService();
        body.setService(service);
        body.setListCustomer(request.getBody().getListCustomer());
        data.setHeader(header);
        data.setBody(body);
        response.setData(data);
        logger.info("<--- response ESBResponse: " + response.toString());
        return response;
    }

    public static ESBSettleBillResponse mapResponse(ESBSettleBillRequest request) {
        logger.info("<--- resquest ESBSettleBillRequest: " + request.toString());
        ESBSettleBillResponse response = new ESBSettleBillResponse();
        ESBSettleBillResponse.DATA data = new ESBSettleBillResponse.DATA();
        ESBSettleBillRequest.Body body = request.getBody();
        ESBSettleBillRequest.Header header = request.getHeader();

        data.setHeader(header);
        data.setBody(body);
        response.setData(data);
        logger.info("<--- response ESBSettleBillResponse: " + response.toString());
        return response;
    }

}
