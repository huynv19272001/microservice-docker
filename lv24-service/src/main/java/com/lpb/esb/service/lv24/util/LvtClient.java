//package com.lpb.esb.service.lv24.util;
//
//
//import com.lpb.middleware.lv24.Execute;
//import com.lpb.middleware.lv24.ExecuteResponse;
//import com.lpb.middleware.lv24.ObjectFactory;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
//
//import javax.xml.bind.JAXBElement;
//
//@Log4j2
//public class LvtClient extends WebServiceGatewaySupport {
//    public JAXBElement<ExecuteResponse> sendRequest(Execute lvtRequest, String url) {
//        JAXBElement<Execute> request = new ObjectFactory().createExecute(lvtRequest);
//        return (JAXBElement<ExecuteResponse>) getWebServiceTemplate().marshalSendAndReceive(url, request);
//    }
//
//}
