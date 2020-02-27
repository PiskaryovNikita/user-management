package com.gongsi.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * SpringBootServletInitializer need to be extended if the application will be deployed to embedded tomcat as WAR.
 * For this type of deployment WAR packaging is required.
 * <p>
 * EnableAutoConfiguration 1. adds beans that are annotated with @ConditionalOnClass
 * if the @Configuration/@Bean/@Component present at class-path
 * 2. add beans that are annotated with @ConditionalOnMissingBean if user doesn't define the bean type
 * HibernateJpaAutoConfiguration excluded since app doesn't need additional jpa beans, app provides its own config
 */
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableConfigurationProperties
public class UserManagementApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // adding @configuration classes(Filter, Servlet, etc.) that will be deployed to embedded Tomcat,
        // UserManagementApplication - the config that contain all beans, hence it's used here
        return application.sources(UserManagementApplication.class);
    }
}
