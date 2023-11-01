package com.lpb.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepresentativeCollectionDTO {
    @JsonProperty("FullName")
    private String fullName;
    @JsonProperty("Position")
    private String position;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("IsReprentative")
    private boolean isReprentative;
}
