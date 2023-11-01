package com.lpb.service.sql.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryVO {
    private String SERVICE_ID;
    private String PRODUCT_CODE;
    private String REQUEST_ACCOUNT;
    private String RECEVIE_ACCOUNT;
    private String EXECUTED_BY;
    private String CONNECTOR_NAME;
    private String URL_API;
    private String CONNECTOR_URL;
    private String CONNECTOR_PORT;
    private String METHOD_ACTION;
    private String TRACE_NO;
    private String UDF1;
    private String UDF2;
    private String UDF3;
    private String UDF4;
    private String UDF5;
    private String UDF6;
    private SQLInfoDTO sqlInfo;
//    @Override
//    public String toString() {
//        return "QueryVO [SERVICE_ID=" + SERVICE_ID + ", PRODUCT_CODE=" + PRODUCT_CODE + ", REQUEST_ACCOUNT=" + REQUEST_ACCOUNT + ", RECEVIE_ACCOUNT=" + RECEVIE_ACCOUNT + ", EXECUTED_BY=" + EXECUTED_BY + ", CONNECTOR_NAME=" + CONNECTOR_NAME + ", URL_API=" + URL_API + ", CONNECTOR_URL=" + CONNECTOR_URL + ", CONNECTOR_PORT=" + CONNECTOR_PORT + ", METHOD_ACTION=" + METHOD_ACTION + ", TRACE_NO=" + TRACE_NO + ", UDF1=" + UDF1 + ", UDF2=" + UDF2 + ", UDF3=" + UDF3 + ", UDF4=" + UDF4 + ", sqlInfo=" + sqlInfo + "]";
//    }
}
