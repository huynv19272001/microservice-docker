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
@JacksonXmlRootElement(localName = "UpdateCustomerInfoRequest")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCustomerInfoREQDTO {
    @JsonProperty("msgId")
    @JacksonXmlProperty(localName = "msgId")
    private String msgId;
    @JsonProperty("action")
    @JacksonXmlProperty(localName = "Action")
    private String action;
    @JsonProperty("CIF")
    @JacksonXmlProperty(localName = "CIF")
    private String cif;
    @JsonProperty("cusType")
    @JacksonXmlProperty(localName = "Cus_Type")
    private String cusType;
    @JsonProperty("person")
    @JacksonXmlProperty(localName = "Person")
    private PersonInfoDTO person;
    @JsonProperty("management")
    @JacksonXmlProperty(localName = "Management")
    private ManagementInfoDTO management;
}
