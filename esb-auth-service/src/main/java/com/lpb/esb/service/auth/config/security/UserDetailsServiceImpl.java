package com.lpb.esb.service.auth.config.security;

import com.lpb.esb.service.auth.model.oracle.EsbUser;
import com.lpb.esb.service.auth.service.EsbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    EsbUserService esbUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        EsbUser esbUser = esbUserService.findUserByUserName(username);
        if (esbUser != null) {
            return new User(esbUser.getUsername()
                , esbUser.getPassword()
                , AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN")
            );
        }

        // If user not found. Throw this exception.
        throw new UsernameNotFoundException("Username: " + username + " not found");
    }
}
