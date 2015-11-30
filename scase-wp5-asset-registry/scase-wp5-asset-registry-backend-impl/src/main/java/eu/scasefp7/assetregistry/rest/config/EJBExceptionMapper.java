package eu.scasefp7.assetregistry.rest.config;


import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.HibernateException;

import eu.scasefp7.base.ScaseException;

/**
 * Martin Moeller.
 * akquinet tech@spree GmbH
 * <p/>
 * Tries to handle different EJBExceptions
 */
@Provider
public class EJBExceptionMapper extends AbstractExceptionMapper implements ExceptionMapper<EJBException> {

    @Inject 
    private HibernateExceptionMapper hibernateExceptionMapper;

    @Inject 
    private PersistenceExceptionMapper persistenceExceptionMapper;

    @Inject 
    private ScaseExceptionMapper scaseExceptionMapper;

    @Override
    public Response toResponse( EJBException e ) {
        final ValidationException validationException = tryToFindException( e, ValidationException.class );
        if ( validationException != null ) {
            return ValidationExceptionMapper.validationExceptionToResponse( validationException );
        }

        final PersistenceException persistenceException = tryToFindException( e, PersistenceException.class );
        if ( persistenceException != null ) {
            return persistenceExceptionMapper.toResponse( persistenceException );
        }
        
        final HibernateException hibernateException = tryToFindException( e, HibernateException.class );
        if ( hibernateException != null ) {
            return hibernateExceptionMapper.toResponse( hibernateException );
        }
        
        final ScaseException sportDBError = tryToFindException( e, ScaseException.class );
        if ( sportDBError != null ) {
            return scaseExceptionMapper.toResponse( sportDBError );
        }

        return super.toDefaultResponse( e );
    }

    public <T extends Throwable> T tryToFindException( EJBException ejbException, Class<T> exceptionType ) {
        Throwable t = ejbException.getCausedByException();
        return tryToFindException(t, exceptionType);
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T tryToFindException(final Throwable t, Class<T> exceptionType)
    {
        Throwable cause = t;
        while (cause != null && !Thread.currentThread().isInterrupted()) {
            if (exceptionType.isInstance(cause)) {
                return (T) cause;
            }
            cause = cause.getCause();
        }

        return null;
    }

    public static Throwable findRoot(final Throwable t)
    {
        Throwable cause = t;
        while (cause.getCause() != null && !Thread.currentThread().isInterrupted()) {
            cause = cause.getCause();
        }
        return cause;
    }
}
