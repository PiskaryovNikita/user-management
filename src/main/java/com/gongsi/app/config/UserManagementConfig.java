package com.gongsi.app.config;

import com.gongsi.app.resource.RoleResource;
import com.gongsi.app.resource.UserResource;
import com.gongsi.app.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserManagementConfig {
    @Bean
    public RoleResource roleResource() {
        return new RoleResource();
    }

    @Bean
    public UserResource userResource(UserService userService) {
        return new UserResource(userService);
    }
}
