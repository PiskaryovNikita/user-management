package com.gongsi.rest.errorHandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.gongsi.rest.errorHandling.exceptions.ResourceAlreadyExistsException;

@Provider
public class ResourceAlreadyExistsExceptionMapper implements ExceptionMapper<ResourceAlreadyExistsException> {

	@Override
	public Response toResponse(ResourceAlreadyExistsException exception) {
		return Response.status(Status.CONFLICT).entity(new ErrorMessage(409, exception.getMessage())).build();
	}
}
