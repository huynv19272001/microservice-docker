package com.lpb.esb.service.auth.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by tudv1 on 2021-07-13
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}