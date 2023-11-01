package com.lpb.service.payoo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoRes extends AbstractBody{
    @JsonProperty(value = "CustomerId")
    private String customerId;

    @JsonProperty(value = "IdentityCode")
    private String identityCode;

    @JsonProperty(value = "Title")
    private String title;

    public String getCustomerId() {
        return customerId;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public String getTitle() {
        return title;
    }
}
