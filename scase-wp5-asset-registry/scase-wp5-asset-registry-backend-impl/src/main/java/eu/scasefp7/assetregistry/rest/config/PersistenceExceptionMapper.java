package eu.scasefp7.assetregistry.rest.config;


import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.HibernateException;

@Provider
public class PersistenceExceptionMapper
        extends AbstractExceptionMapper
        implements ExceptionMapper<PersistenceException>
{

    @Inject
    private HibernateExceptionMapper hibernateExceptionMapper;

    @Override
    public Response toResponse(PersistenceException e)
    {
        if (e instanceof OptimisticLockException) {
            Response.Status status = Response.Status.CONFLICT;
            String message = "Die Daten wurden parallel von einem anderem Anwender geändert. "
                    + "Bitte laden Sie die Daten erneut und wiederholen Sie die Änderung.";
            String detailedMessage = EJBExceptionMapper.findRoot(e).getMessage();
            return toResponse(status, message, detailedMessage);
        } else {
            HibernateException hibernateException = EJBExceptionMapper.tryToFindException(e, HibernateException.class);
            if (hibernateException != null) {
                return this.hibernateExceptionMapper.toResponse(hibernateException);
            }
        }

        return toDefaultResponse(e);
    }
}
