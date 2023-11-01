package com.esb.card.dto.unif.customerinfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "ResultStatus")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultStatusDTO {
    @JsonProperty("success")
    @JacksonXmlProperty(localName = "Success")
    private String success;
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
