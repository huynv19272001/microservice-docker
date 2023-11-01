package com.lpb.service.payoo.utils;

import com.lpb.service.payoo.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Map {

    private static final Logger logger = LogManager.getLogger(Map.class);

    public static ESBResponse mapResponse(ESBRequest request) {
        logger.info("<--- resquest PAYOORequest: " + request.toString());
        ESBResponse response = new ESBResponse();
        ESBResponse.DATA data = new ESBResponse.DATA();
        ESBResponse.Body body = new ESBResponse.Body();
        ESBRequest.Header header = request.getHeader();
        ESBRequest.Service service = request.getBody().getService();
        body.setService(service);
        logger.info("<--- response BodyService: " + body.toString());

        body.setListCustomer(request.getBody().getListCustomer());
        logger.info("<--- response BodyListCustomer: " + body.toString());

        data.setHeader(header);
        data.setBody(body);
        logger.info("<--- response Body: " + body.toString());

        response.setData(data);
        logger.info("<--- response PAYOOResponse: " + response.toString());
        return response;
    }

    public static ESBSettleBillResponse mapResponse(ESBSettleBillRequest request) {
        logger.info("<--- resquest PAYOORequest: " + request.toString());
        ESBSettleBillResponse response = new ESBSettleBillResponse();
        ESBSettleBillResponse.DATA data = new ESBSettleBillResponse.DATA();
        ESBSettleBillRequest.Body body = request.getBody();
        ESBSettleBillRequest.Header header = request.getHeader();
//        ESBSettleBillRequest.Service service = request.getBody().getSettleBill().getService();
//        logger.info("<--- response service: " + service.toString());

        data.setHeader(header);
        data.setBody(body);
        logger.info("<--- response Body: " + body.toString());

        response.setData(data);
        logger.info("<--- response PAYOOResponse: " + response.toString());
        return response;
    }

    public static ESBQueryTransResponse mapResponse(ESBQueryTransRequest request) {
        logger.info("<--- resquest PAYOORequest: " + request.toString());
        ESBQueryTransResponse response = new ESBQueryTransResponse();
        ESBQueryTransResponse.DATA data = new ESBQueryTransResponse.DATA();
        ESBQueryTransRequest.Body body = request.getBody();
        ESBQueryTransRequest.Header header = request.getHeader();
//        ESBSettleBillRequest.Service service = request.getBody().getSettleBill().getService();
//        logger.info("<--- response service: " + service.toString());

        data.setHeader(header);
        data.setBody(body);
        logger.info("<--- response Body: " + body.toString());

        response.setData(data);
        logger.info("<--- response PAYOOResponse: " + response.toString());
        return response;
    }
}

