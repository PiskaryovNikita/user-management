package com.gongsi.rest.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

@ApplicationPath("/webapi")
public class ApplicationConfig extends ResourceConfig {

	public ApplicationConfig() {
		packages("com.nixsolutions.wsxx");
		register(RequestContextFilter.class);
		property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
	}
}
