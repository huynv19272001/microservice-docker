package com.esb.card.dto.unif.updatecardstatus;

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
@JacksonXmlRootElement(localName = "Data")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCardStatusDataDTO {
    @JsonProperty("cardStatus")
    @JacksonXmlProperty(localName = "CardStatus")
    private String cardStatus;
}
