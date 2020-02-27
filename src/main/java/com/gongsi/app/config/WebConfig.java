package com.gongsi.app.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.gongsi.app.deserializer.OffsetDateTimeDeserializer;
import com.gongsi.app.serializer.LocalDateSerializer;
import com.gongsi.app.serializer.OffsetDateTimeSerializer;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // SPRING WEB-MVC
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

    //CXF
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
}
