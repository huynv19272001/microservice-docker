package com.lpb.esb.service.gateway.model.elasticsearch;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Created by tudv1 on 2021-08-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Document(indexName = "esb-system-log")
public class EsbSystemLogEntity {
    @Id
    private String id;
    @Field("app_id")
    private String appId;
    @Field("service_id")
    private String serviceId;
    @Field("trace_id")
    private String traceId;
    private String user;
    @Field("client_ip")
    private String clientIp;
    private String method;
    @Field(name = "request_info")
    private UrlInfo requestUrl;
    @Field("forward_request_info")
    private UrlInfo forwardRequestInfo;
    @Field(name = "time_request", type = FieldType.Date, store = true, format = DateFormat.date_time)
    private Date timeRequest;
    @Field(name = "time_response", type = FieldType.Date, store = true, format = DateFormat.date_time)
    private Date timeResponse;
    @Field("time_execute_milis")
    private long timeExecuteMillis;
    @Field("time_execute_second")
    private double timeExecuteSecond;
    @Field("request_body")
    private String requestBody;
    @Field("response_body")
    private String responseBody;
    @Field("reponse_status_code")
    private int responseStatusCode;

}
