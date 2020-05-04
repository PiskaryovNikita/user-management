package com.gongsi.app.config.jmx;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import javax.management.ObjectName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

@Configuration
public class MBeanConfig {
    @Bean
    public MBeanExporter mbeanExporter(ProfilingController profilingController) {
        MBeanExporter exporter = new MBeanExporter();
        Map<String, Object> beans = new HashMap<>();
        beans.put("profiling:name=ProfilingController", profilingController);
        exporter.setBeans(beans);
        return exporter;
    }

    @Bean
    public LoggingController loggingController() throws Exception {
        LoggingController loggingController = new LoggingController();
        registerMbean(loggingController);
        return loggingController;
    }

    private void registerMbean(LoggingController loggingController) throws Exception {
        ManagementFactory.getPlatformMBeanServer()
                .registerMBean(loggingController,
                        new ObjectName("logging", "name", "LoggingController"));
    }
}
