package eu.scasefp7.assetregistry.rest.config;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import eu.scasefp7.assetregistry.service.exception.NotFoundException;
import eu.scasefp7.base.ScaseException;

@Provider
public class ScaseExceptionMapper extends AbstractExceptionMapper implements ExceptionMapper<ScaseException>
{

    @Override
    public Response toResponse(ScaseException e)
    {
        if (e instanceof NotFoundException) {
            Response.Status status = Response.Status.NOT_FOUND;
            String message = "The entity was not found.";
            String detailedMessage = EJBExceptionMapper.findRoot(e).getMessage();
            return toResponse(status, message, detailedMessage);
        }

        return toDefaultResponse(e);
    }
}
