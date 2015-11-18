package eu.scasefp7.assetregistry.rest.config;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExceptionMapper {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractExceptionMapper.class);

    protected Response toDefaultResponse(Throwable e) {
        LOG.debug("Exception caught ", e);
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        String detailedMessage = EJBExceptionMapper.findRoot(e).getMessage();
        String message = detailedMessage;
        return toResponse(status, message, detailedMessage);
    }
    
    protected Response toResponse(Response.Status status, String message, String detailedMessage) {
        ErrorInfo errorInfo = new ErrorInfo(message);
        errorInfo.setDetailedError(detailedMessage);
        return Response.status(status).entity(errorInfo).build();
    }
    
    protected Response toResponse(Response.Status status, ErrorInfo errorInfo) {
        return Response.status(status).entity(errorInfo).build();
    }
}
