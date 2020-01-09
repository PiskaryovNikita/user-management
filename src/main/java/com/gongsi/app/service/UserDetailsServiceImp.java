package com.gongsi.app.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gongsi.app.model.User;
import com.gongsi.rest.errorHandling.exceptions.DataNotFoundExcpetion;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserService userService;
	private static final Logger logger = Logger.getLogger(UserDetailsServiceImp.class);

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = null;
		try {
			user = userService.findByLogin(userName);
		} catch (DataNotFoundExcpetion e) {
			logger.error("no user with username " + userName, e);
		} catch (Exception e) {
			logger.error("loadUserByUsername", e);
			throw new RuntimeException("excbyload", e);
		}
		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername(userName);
		if (user != null) {
			builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
			String authorities;
			if (user.getRole().getRoleId().equals(2L)) {
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
