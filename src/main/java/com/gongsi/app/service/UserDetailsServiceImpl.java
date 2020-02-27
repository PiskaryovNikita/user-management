package com.gongsi.app.service;

import com.gongsi.app.persistence.model.Role;
import com.gongsi.app.persistence.model.User;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.findByLogin(userName);
        } catch (NotFoundException e) {
            log.error("no user with username " + userName, e);
            throw new NotFoundException("no user with username " + userName, e);
        } catch (Exception e) {
            log.error("unknown error", e);
            throw new InternalServerErrorException("unknown error", e);
        }
        UserBuilder builder;
        builder = org.springframework.security.core.userdetails.User.withUsername(userName);
        if (user != null) {
            builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            String authorities;
            if (user.getRole().equals(Role.ADMIN)) {
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
