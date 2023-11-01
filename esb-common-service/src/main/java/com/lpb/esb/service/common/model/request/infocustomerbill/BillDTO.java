package com.lpb.esb.service.common.model.request.infocustomerbill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDTO<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("billCode")
    private String billCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("billId")
    private String billId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("billDesc")
    private String billDesc;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("billType")
    private String billType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("billStatus")
    private String billStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("billAmount")
    private String billAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("settledAmount")
    private String settledAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("paymentMethod")
    private String paymentMethod;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("otherInfo")
    private String otherInfo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("amt_unit")
    private String amtUnit;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("extraData")
    private Map<String, Object> extraData;
}
