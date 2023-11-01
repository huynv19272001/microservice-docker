package com.lpb.insurance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfoDTO {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private Integer expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("username")
    private String username;
    @JsonProperty("permission")
    private String permission;
    @JsonProperty("permissionUser")
    private String permissionUser;
    @JsonProperty("permissionDvi")
    private String permissionDvi;
    @JsonProperty("permissionDuyetTai")
    private String permissionDuyetTai;
    @JsonProperty("tinhtrang")
    private String tinhTrang;
    @JsonProperty(".issued")
    private Date issued;
    @JsonProperty(".expires")
    private Date expires;
}

