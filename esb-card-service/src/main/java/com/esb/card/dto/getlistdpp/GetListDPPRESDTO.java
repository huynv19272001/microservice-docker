package com.esb.card.dto.getlistdpp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "ArrayOfOperDpp")
public class GetListDPPRESDTO {
    @JsonProperty("requestCode")
    private String requestCode;
    @JsonProperty("cif")
    private String cif;
    @JsonProperty("cardId")
    private String cardId;
    @JsonProperty("operDpp")
    @JacksonXmlProperty(localName = "OperDpp")
    private List<OperDppRESDTO> operDppRES;
}
