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
public class ChangeAccountREQDTO {
    @JsonProperty("accountNumber")
    @JacksonXmlProperty(localName = "AccountNumber")
    private String accountNumber;
    @JsonProperty("accountType")
    @JacksonXmlProperty(localName = "AccountType")
    private String accountType;
    @JsonProperty("accountStatus")
    @JacksonXmlProperty(localName = "AccountStatus")
    private String accountStatus;
    @JsonProperty("isDefaultAccount")
    @JacksonXmlProperty(localName = "IsDefaultAccount")
    private String isDefaultAccount;
}
