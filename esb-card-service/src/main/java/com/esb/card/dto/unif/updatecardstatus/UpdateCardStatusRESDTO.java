package com.esb.card.dto.unif.updatecardstatus;

import com.esb.card.dto.unif.customerinfo.ResultStatusDTO;
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
@JacksonXmlRootElement(localName = "UpdateCardStatusResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCardStatusRESDTO {
    @JsonProperty("resultStatus")
    @JacksonXmlProperty(localName = "ResultStatus")
    private ResultStatusDTO resultStatus;
    @JsonProperty("data")
    @JacksonXmlProperty(localName = "Data")
    private UpdateCardStatusDataDTO data;
}
