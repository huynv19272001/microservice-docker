package com.lpb.esb.service.common.model.request.infocustomerbill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HeaderDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("source")
    private String source;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ubscomp")
    private String ubscomp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("referenceNo")
    private String referenceNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("msgid")
    private String msgid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("correlid")
    private String correlid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("userid")
    private String userid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("branch")
    private String branch;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("moduleid")
    private String moduleid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("service")
    private String service;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("serviceId")
    private String ServiceId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("operation")
    private String operation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("productCode")
    private String productCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("sourceOperation")
    private String sourceOperation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("sourceUserid")
    private String sourceUserid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("destination")
    private String destination;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("multitripid")
    private String multitripid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("functionid")
    private String functionid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("action")
    private String action;
}

