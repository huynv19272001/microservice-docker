package com.lpb.service.payoo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTransactionStatusBERes extends AbstractBody {
    @JsonProperty(value = "ReturnCode")
    private int returnCode;

    @JsonProperty(value = "Status")
    private String status;

    @JsonProperty(value = "OrderNo")
    private String orderNo;

    @JsonProperty(value = "TransactionType")
    private int transactionType;

    @JsonProperty(value = "TransactionTypeName")
    private String transactionTypeName;
    public int getReturnCode() {
        return returnCode;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public int getTransactionType() {
        return transactionType;
    }
    public String getTransactionTypeName() {
        return transactionTypeName;
    }
}
