package com.gongsi.rest.errorHandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;
//
//@Provider //this class is just for debugging
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable e) {
		String stackTraceStr = "";
		for (StackTraceElement elem : e.getStackTrace()) {
			stackTraceStr += elem + "<br>&#10;";
		}
		return Response.status(Status.SERVICE_UNAVAILABLE).entity(new ErrorMessage(503,
				e.getMessage() + " *** " + e.getCause() + " *** " + e + " *** " + stackTraceStr))
				.build();
	}
}
