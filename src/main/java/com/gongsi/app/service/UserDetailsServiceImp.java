package com.gongsi.app.service;

import com.gongsi.app.persistence.model.User;
import com.gongsi.rest.errorHandling.exceptions.DataNotFoundExcpetion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.findByLogin(userName);
        } catch (DataNotFoundExcpetion e) {
            log.error("no user with username " + userName, e);
        } catch (Exception e) {
            log.error("loadUserByUsername", e);
            throw new RuntimeException("excbyload", e);
        }
        UserBuilder builder = null;
        builder = org.springframework.security.core.userdetails.User.withUsername(userName);
        if (user != null) {
            builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            String authorities;
            if (user.getRole().getId().equals(2L)) {
                authorities = "ADMIN";
            } else {
                authorities = "USER";
            }
            builder.authorities(authorities);
        } else {
            builder.authorities("ANONYMOUS");
        }
        return builder.build();
    }
}
