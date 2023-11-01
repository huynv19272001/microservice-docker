package com.lpb.service.mafc.utils;

import com.lpb.service.mafc.model.MAFCRequest;
import com.lpb.service.mafc.model.MAFCResponse;
import com.lpb.service.mafc.model.MAFCSettleBillRequest;
import com.lpb.service.mafc.model.MAFCSettleBillResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Map {

    private static final Logger logger = LogManager.getLogger(Map.class);

    public static MAFCResponse mapResponse(MAFCRequest request) {
        logger.info("<--- resquest MAFCRequest: " + request.toString());
        MAFCResponse response = new MAFCResponse();
        MAFCResponse.DATA data = new MAFCResponse.DATA();
        MAFCResponse.Body body = new MAFCResponse.Body();
        MAFCRequest.Header header = request.getHeader();
        MAFCRequest.Service service = request.getBody().getService();
        body.setService(service);
        body.setListCustomer(request.getBody().getListCustomer());
        data.setHeader(header);
        data.setBody(body);
        response.setData(data);
        logger.info("<--- response MAFCResponse: " + response.toString());
        return response;
    }

    public static MAFCSettleBillResponse mapResponse(MAFCSettleBillRequest request) {
        logger.info("<--- resquest MAFCRequest: " + request.toString());
        MAFCSettleBillResponse response = new MAFCSettleBillResponse();
        MAFCSettleBillResponse.DATA data = new MAFCSettleBillResponse.DATA();
        MAFCSettleBillRequest.Body body = request.getBody();
        MAFCSettleBillRequest.Header header = request.getHeader();

        data.setHeader(header);
        data.setBody(body);
        logger.info("<--- response Body: " + body.toString());

        response.setData(data);
        logger.info("<--- response MAFCResponse: " + response.toString());
        return response;
    }


}
