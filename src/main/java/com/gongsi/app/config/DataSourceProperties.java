package com.gongsi.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data

@Component
@ConfigurationProperties(prefix = "datasource")
public class DataSourceProperties {
    private String url;
    private String username;
    private String password;
    private int poolSize;
    private String driverClassName;
}
