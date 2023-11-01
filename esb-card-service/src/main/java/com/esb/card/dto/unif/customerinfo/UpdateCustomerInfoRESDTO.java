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
@JacksonXmlRootElement(localName = "UpdateCustomerInfoResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCustomerInfoRESDTO {
    @JsonProperty("resultStatus")
    @JacksonXmlProperty(localName = "ResultStatus")
    private ResultStatusDTO resultStatus;
}
