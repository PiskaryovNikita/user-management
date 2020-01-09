package com.gongsi.rest.errorHandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.gongsi.rest.errorHandling.exceptions.DataNotFoundExcpetion;

//raw - unprocessed
//process specified exc
@Provider // - registers excmapper impl-n
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundExcpetion> {

	@Override
	public Response toResponse(DataNotFoundExcpetion exception) {
		return Response.status(Status.NOT_FOUND).entity(new ErrorMessage(404, exception.getMessage())).build();
	}
}
