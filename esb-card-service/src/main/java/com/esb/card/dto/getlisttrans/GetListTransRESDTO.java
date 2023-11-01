package com.esb.card.dto.getlisttrans;

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
@JacksonXmlRootElement(localName = "ArrayOfTransactionDb")
public class GetListTransRESDTO {
    @JsonProperty("requestCode")
    private String requestCode;
    @JsonProperty("cif")
    private String cif;
    @JsonProperty("cardId")
    private String cardId;
    @JsonProperty("transaction")
    @JacksonXmlProperty(localName = "TransactionDb")
    private List<TransactionRESDTO> transactionRESDTO;
}
