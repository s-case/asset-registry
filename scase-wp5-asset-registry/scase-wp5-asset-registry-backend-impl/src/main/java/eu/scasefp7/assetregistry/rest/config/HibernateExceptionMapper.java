package eu.scasefp7.assetregistry.rest.config;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

@Provider
public class HibernateExceptionMapper
        extends AbstractExceptionMapper
        implements ExceptionMapper<HibernateException>
{
 
    private static final Pattern PATTERN = Pattern.compile("UK\\_[a-zA-Z]*\\_([a-zA-Z]*)");
    
    private Map<Integer, String> errorMessages = new HashMap<>();
    {
        // H2 
        errorMessages.put(23505, "The field {0} must be unique!");
        errorMessages.put(23503, "This entry is still referenced!");
        // MySQL
        errorMessages.put(1062, "The field {0} must be unique!");
        errorMessages.put(1451, "This entry is still referenced!");
    }

    @Override
    public Response toResponse(HibernateException e)
    {
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ce = (ConstraintViolationException) e;
            Response.Status status = Response.Status.BAD_REQUEST;
            String message = "Die Aktion hat eine Datenbank-Beschränkung verletzt. ";
            String detailedMessage = getDetailedMessage(ce);
            ErrorInfo errorInfo = new ErrorInfo(message);
            String fieldName = getFieldName(ce);
            if (fieldName != null) {
                errorInfo.addError(fieldName, detailedMessage);
            } else {
                errorInfo.setDetailedError(detailedMessage);
            }
            return toResponse(status, errorInfo);
        }
        return toDefaultResponse(e);
    }
    
    private String getDetailedMessage(ConstraintViolationException e) {
        int errorCode = e.getErrorCode();
        String errorMessage = errorMessages.get(errorCode);
        if (errorMessage != null) {
            errorMessage = MessageFormat.format(errorMessage, getFieldName(e));
        }
        return errorMessage;
    }

    private String getFieldName(ConstraintViolationException e) {
        String fieldName = null;
        String constraintName = ((ConstraintViolationException) e).getConstraintName();
        if (constraintName == null) {
            return null;
        }
        Matcher matcher = PATTERN.matcher(constraintName);
        if (matcher.find()) {
            fieldName = matcher.group(1);
            if (fieldName != null) {
                fieldName = fieldName.toLowerCase();
            }
        }
        return fieldName;
    }
}


