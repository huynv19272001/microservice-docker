package com.lpb.esb.service.lv24.model.lvt.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ResponseHeader {
    private String command;
    private String systemTraceId;
    private String responseDateTime;
    private String transId;
    private String responseCode;
    private String responseMessage;

    @JsonProperty(value = "command")
    public String getCommand() {
        return command;
    }

    @JsonProperty(value = "Command")
    public void setCommand(String command) {
        this.command = command;
    }

    @JsonProperty(value = "system_trace_id")
    public String getSystemTraceId() {
        return systemTraceId;
    }

    @JsonProperty(value = "SystemTraceId")
    public void setSystemTraceId(String systemTraceId) {
        this.systemTraceId = systemTraceId;
    }

    @JsonProperty(value = "response_date_time")
    public String getResponseDateTime() {
        return responseDateTime;
    }

    @JsonProperty(value = "ResponseDateTime")
    public void setResponseDateTime(String responseDateTime) {
        this.responseDateTime = responseDateTime;
    }

    @JsonProperty(value = "trans_id")
    public String getTransId() {
        return transId;
    }

    @JsonProperty(value = "TransId")
    public void setTransId(String transId) {
        this.transId = transId;
    }

    @JsonProperty(value = "response_code")
    public String getResponseCode() {
        return responseCode;
    }

    @JsonProperty(value = "ResponseCode")
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @JsonProperty(value = "response_message")
    public String getResponseMessage() {
        return responseMessage;
    }

    @JsonProperty(value = "ResponseMessage")
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }


}
