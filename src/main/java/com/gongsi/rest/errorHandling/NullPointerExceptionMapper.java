package com.gongsi.rest.errorHandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException> {

	@Override
	public Response toResponse(NullPointerException e) {
		return Response.status(Status.BAD_REQUEST).entity(new ErrorMessage(400, e.getMessage())).build();
	}
}
