package com.lpb.service.mafc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetLoanInforReq extends AbstractBody {
    @JsonProperty(value = "AgreeIdOrCif", required = true)
    private String AgreeIdOrCif;

    @JsonCreator
    public GetLoanInforReq(String AgreeIdOrCif) {
        this.AgreeIdOrCif = AgreeIdOrCif;
    }

    public String getAgreeIdOrCif() {
        return AgreeIdOrCif;
    }

    public void setAgreeIdOrCif(String agreeIdOrCif) {
        AgreeIdOrCif = agreeIdOrCif;
    }
}
