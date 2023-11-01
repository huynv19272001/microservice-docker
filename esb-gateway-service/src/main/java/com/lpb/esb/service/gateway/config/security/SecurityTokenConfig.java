package com.lpb.esb.service.gateway.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity    // Enable security config. This annotation denotes config for spring security.
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            // make sure we use stateless session; session won't be used to store user's state.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // handle an authorized attempts
            .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            // Add a filter to validate the tokens with every request
            .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
            // authorization requests config
            .authorizeRequests()
            // allow all who are accessing "auth" service
            .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
            .antMatchers(HttpMethod.POST, "/esb-kafka-service/**").permitAll()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/ping").permitAll()
            .antMatchers("/esb-ecom-service/**").permitAll()
            .antMatchers("/etax-info-inquiry-service/api/v1/verify-payer-info**").permitAll()
            .antMatchers("/etax-info-inquiry-service/api/v1/link-payer-info**").permitAll()
            .antMatchers("/etax-payment-service/api/v1/init-payment**").permitAll()
            .antMatchers("/etax-payment-service/api/v1/confirm-transaction**").permitAll()
            .antMatchers("/etax-payment-service/api/v1/feedback-transaction**").permitAll()
            .antMatchers("/ms-cbs-customer/**").permitAll()
            .antMatchers("/esb-card-service/api/v1/card/update-limit-payment**").permitAll()
            .antMatchers("/esb-transaction-service/**").permitAll()
            .antMatchers("/mid-forward-service/**").permitAll()
            // Any other request must be authenticated
            .anyRequest().authenticated();
        http.cors();
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }
}
