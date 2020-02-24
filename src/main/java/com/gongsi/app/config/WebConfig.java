package com.gongsi.app.config;

import com.gongsi.app.persistence.model.Role;
import com.gongsi.app.persistence.model.User;
import com.gongsi.app.service.RoleService;
import com.gongsi.app.service.UserService;
import com.gongsi.rest.resource.RoleResource;
import com.gongsi.rest.resource.UserResource;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({"com.gongsi"})
@EnableWebMvc
@EnableAutoConfiguration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/pages/", ".jsp");
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
        org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDB102Dialect");
        builder.addAnnotatedClasses(Role.class, User.class).addProperties(cfg.getProperties());
        return builder.buildSessionFactory();
    }

    @Bean(name = "dataSource")
    public DataSource dataSource(@Value("spring.datasource.driver-class-name") String driver,
                                 @Value("spring.datasource.url") String url,
                                 @Value("spring.datasource.username") String username,
                                 @Value("spring.datasource.password") String password,
                                 @Value("${datasource.poolSize}") int poolSize) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setInitialSize(poolSize);
        return ds;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    // enables validation(annotations), extends cxf server, does not overwrite it
    @Bean
    public JAXRSBeanValidationInInterceptor jaxrsBeanValidationInInterceptor() {
        return new JAXRSBeanValidationInInterceptor();
    }

    @Bean
    public JAXRSBeanValidationOutInterceptor jaxrsBeanValidationOutInterceptor() {
        return new JAXRSBeanValidationOutInterceptor();
    }

    @Bean
    public RoleResource roleResource(RoleService roleService) {
        return new RoleResource(roleService);
    }

    @Bean
    public UserResource userResource(UserService userService) {
        return new UserResource(userService);
    }
}
