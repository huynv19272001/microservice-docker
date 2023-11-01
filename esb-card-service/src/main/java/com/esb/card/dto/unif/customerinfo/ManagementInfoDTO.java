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
@JacksonXmlRootElement(localName = "Management")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManagementInfoDTO {
    @JsonProperty("branchCode")
    @JacksonXmlProperty(localName = "BranchCode")
    private String branchCode;
    @JsonProperty("inUser")
    @JacksonXmlProperty(localName = "InUser")
    private String inUser;
    @JsonProperty("inCore")
    @JacksonXmlProperty(localName = "InCore")
    private String inCore;
    @JsonProperty("approveUser")
    @JacksonXmlProperty(localName = "ApproveUser")
    private String approveUser;
    @JsonProperty("approveCore")
    @JacksonXmlProperty(localName = "ApproveCore")
    private String approveCore;
    @JsonProperty("approveDate")
    @JacksonXmlProperty(localName = "ApproveDate")
    private String approveDate;
}
