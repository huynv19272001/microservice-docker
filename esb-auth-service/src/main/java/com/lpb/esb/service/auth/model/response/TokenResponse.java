package com.lpb.esb.service.auth.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class TokenResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Ho_Chi_Minh")
    private Date issuedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Ho_Chi_Minh")
    private Date expiredTime;
    private long ttl;
    private String tokenHeader;
    private String tokenPrefix;
    private String token;
}
