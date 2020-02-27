package com.gongsi.app.errorHandling;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

//raw - unprocessed
//process specified exc
@Provider // - registers excmapper impl-n
public class DataNotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Status.NOT_FOUND).entity(new ErrorMessage(404, exception.getMessage())).build();
    }
}
