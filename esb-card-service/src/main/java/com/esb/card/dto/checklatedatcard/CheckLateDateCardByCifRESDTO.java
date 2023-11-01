package com.esb.card.dto.checklatedatcard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "CheckLateDateCardByCifResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckLateDateCardByCifRESDTO {
    @JsonProperty("cif")
    @JacksonXmlProperty(localName = "Cif")
    private String cif;
    @JsonProperty("status")
    @JacksonXmlProperty(localName = "Status")
    private String status;
}
