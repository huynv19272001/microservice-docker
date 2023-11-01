package com.lpb.esb.service.auth.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.auth.config.common.StaticApplicationContext;
import com.lpb.esb.service.auth.model.oracle.EsbScanClient;
import com.lpb.esb.service.auth.model.oracle.EsbUser;
import com.lpb.esb.service.auth.model.response.TokenResponse;
import com.lpb.esb.service.auth.service.EsbScanClientService;
import com.lpb.esb.service.auth.service.EsbUserService;
import com.lpb.esb.service.common.utils.StringUtils;
import com.lpb.esb.service.common.utils.rsa.RSAUtil;
import com.lpb.esb.service.common.utils.rsa.SHA1Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Log
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtConfig jwtConfig;
    EsbUserService esbUserService;
    EsbScanClientService esbScanClientService;
    // We use auth manager to validate the user credentials
    private AuthenticationManager authManager;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtConfig jwtConfig) {
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;

        esbUserService = StaticApplicationContext.getContext().getBean(EsbUserService.class);
        esbScanClientService = StaticApplicationContext.getContext().getBean(EsbScanClientService.class);

        // By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
        // In our case, we use "/auth". So, we need to override the defaults.
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
    }

    public Map<String, String> getAllHeader(HttpServletRequest request) {
        Map<String, String> mapHeader = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            mapHeader.put(name, request.getHeader(name));
        }
        return mapHeader;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {

        try {

            // 1. Get credentials from request
            UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);

//            Map<String, String> map = getAllHeader(request);
//            map.forEach((k, v) -> {
//                log.info(String.format("Header %s:%s", k, v));
//            });
            String passwordHashSHA1 = creds.getPassword();
            EsbUser esbUser = esbUserService.findUserByUserName(creds.getUsername());
            if (esbUser != null) {
                passwordHashSHA1 = SHA1Utils.encryptSHA1(RSAUtil.decryptData(creds.getPassword(), esbUser.getKeyRSA()));
            }
            // 2. Create auth object (contains credentials) which will be used by auth manager
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                creds.getUsername()
                , passwordHashSHA1
                , Collections.emptyList());

            // 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
            return authManager
                .authenticate(authToken)
                ;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Upon successful authentication, generate a token.
    // The 'auth' passed to successfulAuthentication() is the current authenticated user.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        Long now = System.currentTimeMillis();
        EsbUser esbUser = esbUserService.findUserByUserName(auth.getName());
        EsbScanClient esbScanClient = esbScanClientService.findEsbScanClientByScanId(esbUser.getScanIp());
        Date iat = new Date(now - 1000);
        long ttl = jwtConfig.getExpiration();
        try {
            if (!StringUtils.isNullOrBlank(esbUser.getUdf3())) {
                long ttlTemp = Long.valueOf(esbUser.getUdf3());
                if (ttlTemp > 0) {
                    ttl = ttlTemp;
                }
            }
        } catch (Exception e) {
            logger.error("error: " + e.getMessage());
        }
        Date exp = new Date(now + ttl * 1000);

        String token = Jwts.builder()
            .setSubject(auth.getName())
            // Convert to list of strings.
            // This is important because it affects the way we get them back in the Gateway.
            .claim("authorities", auth
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList())
            )
            .claim("scan-ip", esbScanClient.getScanIp())
            .claim("from-ip", esbScanClient.getRangeIp() + "." + esbScanClient.getFrom())
            .claim("to-ip", esbScanClient.getRangeIp() + "." + esbScanClient.getTo())
            .setIssuer(jwtConfig.getKongIssuer())
            .setIssuedAt(iat)
            .setExpiration(exp)  // in milliseconds
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret().getBytes())
            .compact();


        TokenResponse tokenResponse = TokenResponse.builder()
            .issuedAt(iat)
            .expiredTime(exp)
            .ttl(ttl)
            .tokenHeader(jwtConfig.getHeader())
            .tokenPrefix(jwtConfig.getPrefix())
            .token(token)
            .build();

        // Add token to header
        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);

        try {
            ObjectMapper objectMapper = StaticApplicationContext.getContext().getBean(ObjectMapper.class);
            // Add body
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(objectMapper.writeValueAsString(tokenResponse));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error when insert body: " + e.getMessage(), e);
        }
    }

    // A (temporary) class just to represent the user credentials
    private static class UserCredentials {
        private String username, password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
