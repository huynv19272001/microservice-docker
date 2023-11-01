package com.esb.card.dto.updatelinkacc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultStatusRESDTO {
    @JsonProperty("status")
    @JacksonXmlProperty(localName = "Status")
    private String status;
    @JsonProperty("errorCode")
    @JacksonXmlProperty(localName = "ErrorCode")
    private String errorCode;
    @JsonProperty("errorMsg")
    @JacksonXmlProperty(localName = "ErrorMsg")
    private String errorMsg;
    @JsonProperty("errorDesc")
    @JacksonXmlProperty(localName = "ErrorDesc")
    private String errorDesc;
}
