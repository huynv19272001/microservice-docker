package com.lpb.esb.service.gateway.config.security;

import com.google.common.io.CharStreams;
import com.lpb.esb.service.common.utils.cache.CachedBodyHttpServletRequest;
import com.lpb.esb.service.gateway.config.StaticApplicationContext;
import com.lpb.esb.service.gateway.model.elasticsearch.UrlInfo;
import com.lpb.esb.service.gateway.service.EsbSystemLogService;
import com.lpb.esb.service.gateway.utils.LogicUtils;
import com.lpb.esb.service.gateway.utils.ZuulConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    EsbSystemLogService esbSystemLogService;
    LogicUtils logicUtils;

    public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        esbSystemLogService = StaticApplicationContext.getContext().getBean(EsbSystemLogService.class);
        logicUtils = StaticApplicationContext.getContext().getBean(LogicUtils.class);
    }

    private Map<String, String> getAllHeader(HttpServletRequest request) {
        Map<String, String> mapHeader = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            mapHeader.put(name, request.getHeader(name));
        }
        return mapHeader;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        if (!request.getServletPath().startsWith("/ping")) {
            getAllHeader(request).forEach((k, v) -> log.info("Header {} : {}", k, v));
        }
        // 1. get the authentication header. Tokens are supposed to be passed in the authentication header
//        boolean isUpload = request.getServletPath().contains("upload");
        boolean isUpload = request.getContentType() != null && request.getContentType().contains(MediaType.MULTIPART_FORM_DATA_VALUE);
        String header = request.getHeader(jwtConfig.getHeader());
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = null;
        if (isUpload) {
            logRequest(request, isUpload);
        } else {
            cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
            logRequest(cachedBodyHttpServletRequest, isUpload);
        }

        // 2. validate the header and check the prefix
        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
//            chain.doFilter(cachedBodyHttpServletRequest, response);        // If not valid, go to the next filter.
            if (isUpload) {
                chain.doFilter(request, response);
            } else {
                chain.doFilter(cachedBodyHttpServletRequest, response);
            }
            return;
        }

        // If there is no token provided and hence the user won't be authenticated.
        // It's Ok. Maybe the user accessing a public path or asking for a token.

        // All secured paths that needs a token are already defined and secured in config class.
        // And If user tried to access without access token, then he won't be authenticated and an exception will be thrown.

        // 3. Get the token
        String token = header.replace(jwtConfig.getPrefix(), "");

        log.info("token: {}", token);

        try {    // exceptions might be thrown in creating the claims if for example the token is expired

            // 4. Validate the token
            Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();

            String username = claims.getSubject();
            if (username != null) {
                @SuppressWarnings("unchecked")
                List<String> authorities = (List<String>) claims.get("authorities");
                String fromIp = (String) claims.get("from-ip");
                String toIp = (String) claims.get("to-ip");
                String clientIp = request.getRemoteAddr();
                log.info("Client IP: {}", clientIp);
                long fromIpLong = ipToLong(fromIp);
                long toIpLong = ipToLong(toIp);
                long ipClientLong = ipToLong(clientIp);

                if (ipClientLong >= fromIpLong && ipClientLong <= toIpLong) {
                    log.info("Client IP [{}] match range ip from [{}] to [{}]", clientIp, fromIp, toIp);
                } else {
                    log.error("Client IP [{}] not match range ip from [{}] to [{}]", clientIp, fromIp, toIp);
//                    throw new Exception("Ip scan not valid");
                }

                // 5. Create auth object
                // UsernamePasswordAuthenticationToken: A built-in object, used by spring to represent the current authenticated / being authenticated user.
                // It needs a list of authorities, which has type of GrantedAuthority interface, where SimpleGrantedAuthority is an implementation of that interface
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username
                    , null
                    , authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );
                auth.setDetails(claims);
                request.setAttribute(ZuulConstants.X_USER, username);
                // 6. Authenticate the user
                // Now, user is authenticated
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            // In case of failure. Make sure it's clear; so guarantee user won't be authenticated
            SecurityContextHolder.clearContext();
        }

        // go to the next filter in the filter chain
//        chain.doFilter(cachedBodyHttpServletRequest, response);
        if (isUpload) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(cachedBodyHttpServletRequest, response);
        }
    }

    public long ipToLong(String ipString) throws UnknownHostException {
        InetAddress ip = InetAddress.getByName(ipString);
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }

    public void logRequest(HttpServletRequest request, boolean isUpload) {
        UrlInfo urlInfo = UrlInfo.builder()
            .scheme(request.getScheme())
            .host(logicUtils.getIpAddrFromUrl(request.getRequestURL().toString()))
            .port(request.getServerPort())
            .path(logicUtils.getFullPath(request))
            .fullUrl(logicUtils.getFullUrl(request))
            .build();
        String requestData = null;
        try {
            if (!isUpload && request.getContentLength() > 0) {
                requestData = CharStreams.toString(request.getReader());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute(ZuulConstants.X_REQUEST_TIME_HEADER, System.currentTimeMillis());
        request.setAttribute(ZuulConstants.X_REQUEST_ORIGINAL_URL, urlInfo);
        request.setAttribute(ZuulConstants.X_REQUEST_BODY, requestData);

        if (!urlInfo.getPath().startsWith("/ping")) {
            log.info("Request: [Client IP: {}] [{}] {}\tbody: {}"
                , request.getRemoteAddr()
                , request.getMethod()
                , logicUtils.getFullUrl(request)
                , requestData);
        }
    }
}
