package com.gongsi.app.config.jmx;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProfilingController {
    private boolean enabled;
}
