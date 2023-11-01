package com.lpb.service.payoo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataRes extends AbstractBody {
    @JsonProperty(value = "Data")
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
