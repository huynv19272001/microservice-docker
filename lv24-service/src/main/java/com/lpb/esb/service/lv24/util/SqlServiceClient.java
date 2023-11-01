//package com.lpb.esb.service.lv24.util;
//
//
//
//import com.lpb.middleware.sqlservice.DMLCOMMANDREQ;
//import com.lpb.middleware.sqlservice.DMLCOMMANDRES;
//import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
//
//public class SqlServiceClient extends WebServiceGatewaySupport {
//
//    public DMLCOMMANDRES dmlRequest(String url, DMLCOMMANDREQ request) {
//
//        return (DMLCOMMANDRES) getWebServiceTemplate().marshalSendAndReceive(url, request);
//
//    }
//
//}
