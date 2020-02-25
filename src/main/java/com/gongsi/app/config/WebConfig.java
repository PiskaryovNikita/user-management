package com.gongsi.app.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.gongsi.app.deserializer.OffsetDateTimeDeserializer;
import com.gongsi.app.persistence.model.Role;
import com.gongsi.app.persistence.model.User;
import com.gongsi.app.resource.RoleResource;
import com.gongsi.app.resource.UserResource;
import com.gongsi.app.serializer.LocalDateSerializer;
import com.gongsi.app.serializer.OffsetDateTimeSerializer;
import com.gongsi.app.service.RoleService;
import com.gongsi.app.service.UserService;
import java.time.LocalDate;
import java.time.OffsetDateTime;
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
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan({"com.gongsi"})
@EnableAutoConfiguration
@EnableTransactionManagement
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/pages/", ".jsp");
    }

    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);

        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure();

        builder.addAnnotatedClasses(Role.class, User.class)
                .addProperties(configuration.getProperties());

        return builder.buildSessionFactory();
    }

    @Bean
    public DataSource dataSource(@Value("${datasource.driver-class-name}") String driver,
                                 @Value("${datasource.url}") String url,
                                 @Value("${datasource.username}") String username,
                                 @Value("${datasource.password}") String password,
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

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer(OffsetDateTime.class));
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer(LocalDate.class));
        simpleModule.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer(OffsetDateTime.class));
        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }

    //CXF only converts to xml by default
    // Jackson provides java to json conversion
    @Bean
    public JacksonJsonProvider jacksonJsonProvider(ObjectMapper objectMapper) {
        JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
        jsonProvider.setMapper(objectMapper);
        return jsonProvider;
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
