package com.gongsi.app.config.jmx;

import lombok.Data;

@Data
public class LoggingController implements LoggingControllerMBean {
    private boolean enabled;
}
