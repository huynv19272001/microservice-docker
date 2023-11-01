package com.esb.card.dto.updatelinkacc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "Data")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataRESDTO {
    @JsonProperty("listAccounts")
    @JacksonXmlProperty(localName = "Accounts")
    List<AccountsRESDTO> listAccountsRESDTO;
}
