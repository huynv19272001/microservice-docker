package com.lpb.esb.service.auth.config.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JwtConfig {
    @Value("${security.jwt.uri:/esb-auth-service/**}")
    private String uri;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${security.jwt.secret:LpbEsbJwtSecretKey}")
    private String secret;

    @Value("${jwt.issuer}")
    private String kongIssuer;
}
