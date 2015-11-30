package eu.scasefp7.assetregistry.rest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;
import java.util.Set;

/**
 * Martin Moeller.
 * akquinet tech@spree GmbH
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    private static final Logger LOG = LoggerFactory.getLogger( ValidationExceptionMapper.class );

    @Override
    public Response toResponse( ValidationException ve ) {
        LOG.debug( "validation exception caught", ve );

        return validationExceptionToResponse( ve );
    }

    public static Response validationExceptionToResponse( ValidationException ve ) {
        final ErrorInfo content = new ErrorInfo( ve.getClass().getSimpleName() );

        if ( ve instanceof ConstraintViolationException ) {
            handle( (ConstraintViolationException) ve, content );
        }

        return Response.status( Response.Status.BAD_REQUEST ).entity( content ).build();
    }

    private static void handle( ConstraintViolationException ve, ErrorInfo errors ) {
        Set<ConstraintViolation<?>> constraintViolations = ve.getConstraintViolations();

        for ( ConstraintViolation<?> violation : constraintViolations ) {
            errors.addError( propertyName( violation.getPropertyPath() ), violation.getMessage() );
        }
    }

    private static String propertyName( Path path ) {
        Iterator<Path.Node> iterator = path.iterator();
        String propertyNameCandidate = null;

        while ( iterator.hasNext() ) {
            propertyNameCandidate = iterator.next().getName();
        }
        return propertyNameCandidate;
    }
}
